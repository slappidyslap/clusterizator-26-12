package kg.musabaev.cluserizator.view

import de.saxsys.mvvmfx.JavaView
import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.beans.binding.Bindings
import javafx.fxml.Initializable
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType.WARNING
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import javafx.util.Duration
import kg.musabaev.cluserizator.domain.GraphClusterItem
import kg.musabaev.cluserizator.domain.GraphClusters
import kg.musabaev.cluserizator.util.removeStyle
import kg.musabaev.cluserizator.util.setStyle
import kg.musabaev.cluserizator.viewmodel.*
import java.net.URL
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ToolBarView() : HBox(), Initializable, JavaView<ToolBarViewModel> {

    private val regexInput = TextField("regex")
    private val addNodeBtn = Button("Создать")
    private val deleteNodeBtn = Button("Удалить")

    private lateinit var toolBarViewModel: ToolBarViewModel
    private lateinit var seoKeywordTableViewModel: SeoKeywordTableViewModel
    private lateinit var graphClusters: GraphClusters
    private lateinit var graphViewModel: GraphViewModel

    @Inject
    constructor(
        toolBarViewModel: ToolBarViewModel,
        seoKeywordTableViewModel: SeoKeywordTableViewModel,
        graphClusters: GraphClusters,
        graphViewModel: GraphViewModel
    ) : this() {
        this.toolBarViewModel = toolBarViewModel
        this.seoKeywordTableViewModel = seoKeywordTableViewModel
        this.graphClusters = graphClusters
        this.graphViewModel = graphViewModel
    }

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        super.getChildren().addAll(regexInput, deleteNodeBtn, addNodeBtn)
        initListeners()
    }

    private fun initListeners() {
        initBtnsColorsListeners()
        initAddNodeBtnListeners()
        initDeleteNodeBtnListeners()
    }

    private fun initAddNodeBtnListeners() {
        // Есть нода не выбрана, то кнопка должна быть отключена
        addNodeBtn.disableProperty().bind(
            Bindings.isEmpty(graphViewModel.selectedClusterIdProperty()))

        // При нажатии на кнопку, добавить новую ноду и изменить таблицу
        addNodeBtn.setOnAction {
            val clusterId = regexInput.text
            // Ищем все ключевые слова в таблице в соответствии regex
            val matchedKeywords = seoKeywordTableViewModel
                .keywords
                .filter { it.getKeyword().contains(Regex(regexInput.text)) }
            val parentClusterId = graphViewModel.getSelectedClusterId()

            // В глобальное хранилище добавляем новую ноду - кластеризуем ключи
            val newCluster = GraphClusterItem(parentClusterId, clusterId, matchedKeywords)
            graphClusters[clusterId] = newCluster

            // Раз кластеризовали ключи, удаляем из старого кластера и добавляем дочернюю ноду в список из родительской ноды
            val parentCluster = graphClusters[parentClusterId]!!
            parentCluster.seoKeywords().removeAll(matchedKeywords)
            parentCluster.neighbors().add(newCluster)
        }
    }

    private fun initDeleteNodeBtnListeners() {
        deleteNodeBtn.disableProperty().bind(
            Bindings.isEmpty(graphViewModel.selectedClusterIdProperty()))

        // При нажатии на кнопку, удалить выбранную ноду изменить таблицу
        deleteNodeBtn.setOnAction {
            val selectedCluster =  graphClusters[graphViewModel.getSelectedClusterId()]!!
            val parentCluster = graphClusters[selectedCluster.getParentId()]

            if (parentCluster == null || selectedCluster.neighbors().isNotEmpty()) {
                val dialog = Alert(WARNING, "Нельзя удалять кластеры у которого есть дочерние кластеры")
                dialog.show()
                return@setOnAction
            }

            // Берем все ключевые слова удаляемой ноды
            val selectedSeoKeywords = seoKeywordTableViewModel.keywords

            // Добавляем ключевые слова в список ключевых слов родителя удаляемой ноды
            parentCluster.seoKeywords().addAll(selectedSeoKeywords)

            // Удаляем из мапы и из списка соседей у родителя
            graphClusters.getMap().remove(selectedCluster.getId())
            parentCluster.neighbors().remove(selectedCluster)
        }
    }

    private fun initBtnsColorsListeners() {
        /*graphViewModel.selectedClusterIdProperty().addListener { _, _, selectedClusterId ->
            val colors = getBgAndFgColorByString(selectedClusterId)

//            animateBtnsFill(colors.first, colors.second)

            toolBarViewModel.setBtnsBgColor(colors.first)
            toolBarViewModel.setBtnsFgColor(colors.second)
        }

        toolBarViewModel.btnsColorBgProperty().addListener { _, _, selectedClusterId ->
            if (selectedClusterId == null) {
                addNodeBtn.removeStyle("-fx-background-color")
                deleteNodeBtn.removeStyle("-fx-background-color")
            }
            else {
                addNodeBtn.setStyle("-fx-background-color", getRgbStringByColor(selectedClusterId))
                deleteNodeBtn.setStyle("-fx-background-color", getRgbStringByColor(selectedClusterId))
            }
        }
        toolBarViewModel.btnsColorFgProperty().addListener { _, _, selectedClusterId ->
            if (selectedClusterId == null) {
                addNodeBtn.removeStyle("-fx-text-fill")
                deleteNodeBtn.removeStyle("-fx-text-fill")
            }
            else {
                addNodeBtn.setStyle("-fx-text-fill", getRgbStringByColor(selectedClusterId))
                deleteNodeBtn.setStyle("-fx-text-fill", getRgbStringByColor(selectedClusterId))
            }
        }*/
    }

    private fun getBgAndFgColorByString(input: String): Pair<Color, Color> {
        var hash = 0
        for (c in input.toCharArray()) {
            hash = c.code + ((hash shl 5) - hash)
        }

        // Преобразуем хэш в RGB
        val r = (hash and 0xFF0000) shr 16
        val g = (hash and 0x00FF00) shr 8
        val b = hash and 0x0000FF

        // Генерируем контрастный цвет
        val bgColor: Color = Color.rgb(r, g, b)

        // Проверяем яркость для обеспечения читаемости текста
        val brightness: Double =
            0.2126 * bgColor.red +
                    0.7152 * bgColor.green +
                    0.0722 * bgColor.blue

        // Если фон слишком тёмный, делаем текст белым, иначе - чёрным
        val fgColor = if (brightness < 0.5) Color.WHITE else Color.BLACK

        return Pair(bgColor, fgColor)
    }

    private fun getRgbStringByColor(color: Color): String {
        return "rgb(${color.red * 255},${color.green * 255},${color.blue * 255})"
    }

    private fun animateBtnsFill(bgColor: Color, fgColor: Color) {
        val timeline = Timeline(
            KeyFrame(Duration.seconds(0.5), KeyValue(toolBarViewModel.btnsColorBgProperty(), bgColor)),
            KeyFrame(Duration.seconds(0.5), KeyValue(toolBarViewModel.btnsColorFgProperty(), fgColor)))
        timeline.play()
    }
}
