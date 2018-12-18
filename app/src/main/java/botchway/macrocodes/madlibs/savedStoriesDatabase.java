package botchway.macrocodes.madlibs;

/**
 * Created by Botchway on 8/3/2017.
 */

public interface savedStoriesDatabase {

     String DBName = "savedStories";
     String tableName = "stories";
     String listNameCol = "listName";
     String storyCol = "story";

    String createTable = "CREATE TABLE " +tableName +" ( " + listNameCol +" TEXT NOT NULL," +
                                                          storyCol + " TEXT NOT NULL )";

    String getAllColumns = "SELECT * FROM "+ DBName ;


}
