package kg.musabaev.cluserizator

import de.saxsys.mvvmfx.FluentViewLoader
import de.saxsys.mvvmfx.easydi.MvvmfxEasyDIApplication
import javafx.scene.Scene
import javafx.stage.Stage
import kg.musabaev.cluserizator.graph.GraphView
import org.scenicview.ScenicView

class Launcher : MvvmfxEasyDIApplication() {
    @Throws(Exception::class)
    override fun startMvvmfx(stage: Stage) {
        val viewTuple = FluentViewLoader.javaView(MainView::class.java).load()
        FluentViewLoader.javaView(GraphView::class.java).load()

        stage.title = "Hello!"
        val scene = Scene(viewTuple.view)
        stage.scene = scene
        stage.isMaximized = true
        stage.show()
//        ScenicView.show(scene)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(Launcher::class.java)
        }
        // fun main() = launch(Launcher::class.java)
    }
}