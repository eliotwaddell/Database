//Programmer Eliot J Waddell
//wadde040@umn.edu
import java.io.IOException;
import java.util.*;
public class Main
{
    public static void main(String[] args) throws IOException//all main method does is have a continuation loop as well as parse queries from scanner
    {
        Scanner s = new Scanner(System.in);
        Database d = new Database();
        boolean again = true;
        while(again)//continuation loop
        {
            InterpretedQuery q = QueryEvaluator.evaluateQuery(s.nextLine());//each line is its own query
            QueryType type = q.getQueryType();
            if(type==QueryType.EXIT_STATEMENT)//each conditional executes its respective method
            {
                again=false;
            }
            else if(type==QueryType.SELECT_STATEMENT)
            {
                d.select(q.getTableName(),q.getColumnNames(),q.getConditional());
            }
            else if(type==QueryType.PRINT_STATEMENT)
            {
                d.print(q.getTableName());
            }
            else if(type==QueryType.STORE_STATEMENT)
            {
                d.store(q.getTableName());
            }
            else if(type==QueryType.LOAD_STATEMENT)
            {
                d.load(q.getFileName());
            }
            else if(type==QueryType.INSERT_STATEMENT)
            {
                d.insert(q.getInsertValues(),q.getTableName());
            }
            else if(type==QueryType.CREATE_STATEMENT)
            {
                d.createTable(q.getColumnNames(),q.getColumnTypes(),q.getTableName());
            }
        }
    }
}
