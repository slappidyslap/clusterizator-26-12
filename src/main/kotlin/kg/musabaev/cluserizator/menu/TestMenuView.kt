package kg.musabaev.cluserizator.menu

import javafx.collections.FXCollections.observableArrayList
import kg.musabaev.cluserizator.saveload.TestCsvFileHandler
import kg.musabaev.cluserizator.viewmodel.GraphClusterMap
import kg.musabaev.cluserizator.viewmodel.GraphClusterValue
import kg.musabaev.cluserizator.viewmodel.SeoKeywordModel
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestMenuView() : MenuView() {

    private lateinit var graphClusterMap: GraphClusterMap

    @Inject
    constructor(graphClusterMap: GraphClusterMap) : this() {
        this.graphClusterMap = graphClusterMap
    }

    override fun loadFile() {
        val i = AtomicReference(1)
        val a = observableArrayList<SeoKeywordModel>()
        TestCsvFileHandler().getLinesCsv().forEach { line ->
            println("initTableView$i")
            val values = line.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val otherMetas = values.copyOfRange(1, values.lastIndex - 1)
            val id = i.get()
            val keyword = values[0]

            val seoKeyword = SeoKeywordModel(id.toString(), keyword, otherMetas)
            i.getAndSet(i.get() + 1)
            a.add(seoKeyword)
        }
        val node22 = GraphClusterValue(
            clusterId = "22",
            seoKeywords = observableArrayList(a.subList(a.size / 2, a.size)),
            neighborClusterIds = emptyList())
        graphClusterMap.map["11"] = GraphClusterValue(
            clusterId = "11",
            seoKeywords = observableArrayList(a.subList(0, a.size / 2)),
            neighborClusterIds = listOf("22"))
        graphClusterMap.map["22"] = node22
    }

    override fun saveFile() {

    }
}