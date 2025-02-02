package kg.musabaev.cluserizator.viewmodel

import de.saxsys.mvvmfx.ViewModel
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import kg.musabaev.cluserizator.domain.SeoKeyword
import javax.inject.Singleton

@Singleton
class SeoKeywordTableViewModel : ViewModel {
    val keywords: ObservableList<SeoKeyword> = FXCollections.observableArrayList()
}