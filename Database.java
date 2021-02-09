import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Database
{
    String[] tableNames = new String[0];//array to store the names of the tables. index corresponds to actual table index
    Table[] database = new Table[0];//stores tables
    public Database()//database doesn't need to be constructed, every attribute is covered in the methods
    {

    }
    public void createTable(String[] n, String[] t, String na)//takes elements from the query and constructs a table, the schema, the types and the name
    {
        if(database.length==0||(!(ArrayManipulator.contains(tableNames,na))))//makes sure table isn't already in database
        {
            if(ArrayManipulator.unique(n).equalsIgnoreCase(""))//makes sure table has no duplicate elements
            {
                if(ArrayManipulator.validTypes(t).equalsIgnoreCase(""))//makes sure the types are valid
                {
                    Table newTable = new Table(n, t, na);//constructs a new table
                    database = ArrayManipulator.tableAppend(database,newTable);//appends table to database, and name to table names
                    tableNames = ArrayManipulator.stringAppend(tableNames,na);
                }
                else
                {
                    System.out.println(ArrayManipulator.validTypes(t)+" is not a valid type");
                }
            }
            else
            {
                System.out.println(ArrayManipulator.unique(n)+" is not a unique column name");
            }
        }
        else
        {
            System.out.println(na+" already exists in the database");
        }
    }
    public void insert(String[] d, String name)
    {
        int index = ArrayManipulator.indexOf(tableNames,name);//this snippet of code is used to determine which table should run the corresponding method
        database[index].insert(d);
    }
    public void print(String name)
    {
        int index = ArrayManipulator.indexOf(tableNames,name);
        database[index].print();
    }
    public void store(String name) throws IOException
    {
        int index = ArrayManipulator.indexOf(tableNames,name);
        database[index].store();
    }
    public void load(String fName) throws IOException
    {
        File file = new File(fName);//creates file object
        FileReader f = new FileReader(file);//filereader constructed using file
        BufferedReader b = new BufferedReader(f);//buffered reader constructed using filereader
        String columnNames="";//both start as strings that will be split in future
        String columnTypes="";
        String[] data = {};//this must be initialized as an array because it will be split into a 2d array (rows)
        int numLines = 0;
        b.mark(1000);//marks a point for the filereader to reset to when it checks the while conditional
        while(b.readLine()!=null)//if there is still text to be read
        {
            b.reset();//resets to mark
            if(numLines==0)//numlines being zero means it is the first line, guaranteed to be the schema
            {
                columnNames = b.readLine();
                numLines++;
                b.mark(1000);
            }
            else if(numLines==1)//numlines=1 means it is guaranteed to be the types
            {
                columnTypes = b.readLine();
                numLines++;
                b.mark(1000);
            }
            else//if it has anything else it is part of the data and is appended to the string array
            {
                data = ArrayManipulator.stringAppend(data,b.readLine());
                numLines++;
                b.mark(1000);
            }
        }
        String[] cNames = columnNames.split(",");//splits column names
        String[] tNames = columnTypes.split(",");//splits type names
        fName = fName.replace(".db","");//replaces the filename variable so table doesn't have .db extension
        createTable(cNames,tNames,fName);//creates table using parsed data
        int index = ArrayManipulator.indexOf(tableNames,fName);
        for(int i=0;i<data.length;i++)
        {
            database[index].insert(data[i].split(","));//inserts each row
        }
    }

    public void select(String tName, String[] columns, String conditional)
    {
        int index = ArrayManipulator.indexOf(tableNames,tName);
        database[index].select(columns,conditional);
    }
}