module kg.musabaev.cluserizator {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.scenicview.scenicview;
    requires de.saxsys.mvvmfx;
    requires de.saxsys.mvvmfx.easydi;
    requires javax.inject;
    requires kotlin.stdlib;
    requires jdk.jsobject;
    requires easy.di;
    requires com.alibaba.fastjson2;

    exports kg.musabaev.cluserizator;
    exports kg.musabaev.cluserizator.view;
    exports kg.musabaev.cluserizator.viewmodel;
    exports kg.musabaev.cluserizator.domain;
    exports kg.musabaev.cluserizator.menu;
    exports kg.musabaev.cluserizator.saveload;

    opens kg.musabaev.cluserizator to de.saxsys.mvvmfx;
    opens kg.musabaev.cluserizator.view to de.saxsys.mvvmfx;
    opens kg.musabaev.cluserizator.viewmodel to de.saxsys.mvvmfx;

}