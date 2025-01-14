package kg.musabaev.cluserizator.menu

import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.JSONReader.Feature.AllowUnQuotedFieldNames
import com.alibaba.fastjson2.JSONWriter.Feature.UnquoteFieldName
import javafx.collections.FXCollections.observableArrayList
import javafx.fxml.Initializable
import javafx.scene.control.Menu
import javafx.scene.control.MenuItem
import kg.musabaev.cluserizator.saveload.TestCsvFileHandler
import kg.musabaev.cluserizator.domain.GraphClusters
import kg.musabaev.cluserizator.domain.GraphClusterItem
import kg.musabaev.cluserizator.domain.SeoKeyword
import kg.musabaev.cluserizator.file.CsvHandler
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.FileInputStream
import java.io.FileOutputStream
import java.net.URL
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveLoadTestMenuView() : MenuView(), Initializable {

    private lateinit var graphClusters: GraphClusters
    private lateinit var menuViewModel: MenuViewModel

    @Inject
    constructor(graphClusters: GraphClusters, menuViewModel: MenuViewModel) : this() {
        this.graphClusters = graphClusters
        this.menuViewModel = menuViewModel

        super.getMenus().add(Menu("Для разработчиков").apply {
            items.addAll(
                MenuItem("Пример графа").apply { setOnAction {
                    val a = observableArrayList<SeoKeyword>()
                    TestCsvFileHandler().getLinesCsv().forEach { line ->
                        val values = line.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toList()
                        val otherMetas = values.subList(1, values.lastIndex - 1)
                        val keyword = values[0]

                        val seoKeyword = SeoKeyword(keyword, otherMetas)
                        a.add(seoKeyword)
                    }
                    graphClusters["root"] = GraphClusterItem(
                        id = "root",
                        seoKeywords = observableArrayList(a))
                }},
                MenuItem("Удалить все кластеры").apply { setOnAction { TODO() } }
            )
        }) // TODO мб потом перенести в initialize
    }

    override fun loadProject() {
        menuViewModel.setIsLoadingFromSave(true)
        BufferedInputStream(FileInputStream("test.seoclztr")).use { input ->
            val root = JSON.parseObject<GraphClusterItem>(
                input,
                GraphClusterItem::class.java,
                AllowUnQuotedFieldNames)
            putClustersRecursively(root)
            graphClusters["root"] = root
        }
        menuViewModel.setIsLoadingFromSave(false)
    }

    override fun saveProject() {
        // TODO() тут надо изучить какой размера буфера можно выделить
        BufferedOutputStream(FileOutputStream("test.seoclztr"), 128).use { output ->
            JSON.writeTo(
                output,
                graphClusters["root"],
                UnquoteFieldName
            )
        }
    }

    override fun importCsv() {
        val seoKeywords = mutableListOf<SeoKeyword>()
        CsvHandler("proseller.csv")
            .linesAsSequence()
            .forEachIndexed { i, lines ->
                if (i == 0) {
                    graphClusters.keywordContext.clear()
                    graphClusters.keywordContext.addAll(lines)
                } else {
                    val keyword = lines[0]
                    val otherMetas = lines.subList(1, lines.lastIndex)
                    seoKeywords.add(SeoKeyword(keyword, otherMetas))
                }
            }
        graphClusters.clear() // TODO диалог окно на создание нового приложения/окн
        graphClusters["root"] = GraphClusterItem("root", seoKeywords)
    }

    override fun exportProject() {
        TODO("Not yet implemented")
    }

    private fun putClustersRecursively(cluster: GraphClusterItem) {
        if (cluster.neighbors().isEmpty()) return
        for (neighbor in cluster.neighbors()) {
            putClustersRecursively(neighbor)
            graphClusters[neighbor.getId()] = neighbor
        }
    }

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
//        TODO() срабатывает
    }
}