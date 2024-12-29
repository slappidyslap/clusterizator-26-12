package kg.musabaev.cluserizator;

import java.util.stream.Stream;

public class TestCsvFileHandler implements FileHandler {
    @Override
    public Stream<String> getLinesCsv() {
        return Stream.of(
                "Keyword,Currency,Avg. monthly searches,Изменение за квартал,Изменение за год,Competition,Competition (indexed value),Top of page bid (low range),Top of page bid (high range),Ad impression share,Organic impression share,Organic average position,In account?,In plan?,Searches: Oct 2023,Searches: Nov 2023,Searches: Dec 2023,Searches: Jan 2024,Searches: Feb 2024,Searches: Mar 2024,Searches: Apr 2024,Searches: May 2024,Searches: Jun 2024,Searches: Jul 2024,Searches: Aug 2024,Searches: Sep 2024",
                "купить ключи игры,USD,590,0%,-46%,Низкий,16,\"0,05\",\"0,28\",,,,,,720,720,880,880,720,720,590,590,480,390,480,390",
                "купить ключи стим,USD,9900,0%,0%,Низкий,20,\"0,06\",\"0,28\",,,,,,12100,12100,12100,12100,12100,12100,9900,9900,9900,9900,9900,9900",
                "купить гта 5 на пк,USD,2400,-32%,-19%,Низкий,21,\"0,06\",\"0,26\",,,,,,2400,2900,3600,3600,2400,2400,1900,1600,1900,1900,1600,1300",
                "hogwarts legacy купить ключ,USD,320,24%,0%,Низкий,12,,,,,,,,260,390,590,720,390,260,170,170,210,170,210,210",
                "купить игры стим,USD,8100,22%,83%,Низкий,6,\"0,06\",\"0,33\",,,,,,6600,6600,9900,9900,9900,9900,8100,8100,9900,8100,8100,9900",
                "squad купить ключ,USD,590,0%,50%,Низкий,29,\"0,09\",\"0,29\",,,,,,480,390,480,590,720,390,880,720,720,390,390,390",
                "7 days to die ключ стим,USD,50,-44%,67%,Низкий,12,,,,,,,,40,50,20,40,40,40,40,70,40,90,70,50",
                "7 days to die ключ стим купить,USD,10,∞,0%,Низкий,14,,,,,,,,10,30,20,10,10,10,10,10,10,0,10,10",
                "7 days to die купить ключ steam,USD,20,-50%,100%,Низкий,32,,,,,,,,10,10,10,10,10,10,20,20,20,40,50,20",
                "7 days to die купить ключ стим,USD,50,-50%,250%,Низкий,29,,,,,,,,20,30,70,50,20,30,40,50,30,140,140,70",
                "a plague tale innocence купить ключ,USD,20,-33%,0%,Низкий,2,,,,,,,,30,10,10,20,20,30,20,30,20,30,10,20",
                "a plague tale requiem купить ключ,USD,40,-50%,-50%,Низкий,26,,,,,,,,50,70,50,70,50,70,30,40,20,40,10,20"
        );

    }
}
