package de.hawlandshut.pluto22_gwk.testdata;

import java.util.ArrayList;
import java.util.List;

import de.hawlandshut.pluto22_gwk.model.Post;

public class PostTestData {
    public static List<Post> postTestList = new ArrayList<Post>();

    static String body = "Lorem ipsum dolor sit amet.";

    public static void createTestData(){
        long time =  new java.util.Date().getTime();
        postTestList.add( new Post("1","Author 1", "Title 1", body, time++,""));
        postTestList.add( new Post("2","Author 2", "Title 2", body, time++,""));
        postTestList.add( new Post("3","Author 3", "Title 3", body, time++,""));
        postTestList.add( new Post("4","Author 4", "Title 4", body, time++,""));
        postTestList.add( new Post("5","Author 5", "Title 5", body, time++,""));
    }
}
