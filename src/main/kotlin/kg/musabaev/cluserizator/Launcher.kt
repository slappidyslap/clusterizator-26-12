package kg.musabaev.cluserizator

import de.saxsys.mvvmfx.FluentViewLoader
import de.saxsys.mvvmfx.easydi.MvvmfxEasyDIApplication
import eu.lestard.easydi.EasyDI
import javafx.scene.Scene
import javafx.stage.Stage
import kg.musabaev.cluserizator.menu.MenuView
import kg.musabaev.cluserizator.menu.TestMenuView
import kg.musabaev.cluserizator.view.GraphView
import kg.musabaev.cluserizator.view.MainView
import kg.musabaev.cluserizator.view.SeoKeywordTableView
import kg.musabaev.cluserizator.viewmodel.SeoClusterMapModel

class Launcher : MvvmfxEasyDIApplication() {
    @Throws(Exception::class)
    override fun startMvvmfx(stage: Stage) {
//        val easyDi = EasyDI();
//        MvvmFX.setCustomDependencyInjector{easyDi}
//        MvvmFX.setCustomDependencyInjector { easyDi }

        val viewTuple = FluentViewLoader.javaView(MainView::class.java).load()
        FluentViewLoader.javaView(GraphView::class.java).load()
        FluentViewLoader.javaView(SeoKeywordTableView::class.java).load()
        FluentViewLoader.javaView(TestMenuView::class.java).load()

        stage.title = "Hello!"
        val scene = Scene(viewTuple.view)
        scene.stylesheets.add("style.css")
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

    override fun initEasyDi(context: EasyDI) {
        context.bindProvider(MenuView::class.java) {
            val clazz = context.getInstance(SeoClusterMapModel::class.java)
            TestMenuView(clazz);
        }
    }
}
