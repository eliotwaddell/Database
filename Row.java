public class Row//row class is the collection of elements for every row of every table
{
    public Object[] elements; //any type of object can fit into row
    public Row(String[] d, String[] t)
    {
        elements = rowCreation(d,t);
    }
    public Object[] rowCreation(String[] data, String[] types)//takes data and types provided from query and casts the data to the respective type
    {
        Object[] a = new Object[data.length];
        for(int i=0;i<data.length;i++)
        {
            if(types[i].equalsIgnoreCase("int"))
            {
                a[i] = Integer.valueOf(data[i]);
            }
            if(types[i].equalsIgnoreCase("boolean"))
            {
                a[i] = Boolean.valueOf(data[i]);
            }
            if(types[i].equalsIgnoreCase("double"))
            {
                a[i] = Double.valueOf(data[i]);
            }
            if(types[i].equalsIgnoreCase("String"))
            {
                a[i]=data[i];
            }
        }
        return a;
    }
    public String[] getColumnMembers(String[] selectColumns, String[] allColumns)//this method is used for selection, gets the column members specified by the selection query
    {//and passes them back into an easily accessible array
        String[] members = new String[selectColumns.length];
        for(int i=0;i<selectColumns.length;i++)
        {
            members[i]=(elements[ArrayManipulator.indexOf(allColumns,selectColumns[i])]).toString();
        }
        return members;
    }
    public void print()//print function that is used in the database print function
    {
        for(int i=0;i<elements.length;i++)//simple for loop that prints the contents of an array
        {
            System.out.print(elements[i]);
            if(i!=elements.length-1)
            {
                System.out.print(",");//prints a comma if element is not the last one in the row
            }
        }
        System.out.print("\n");//new line to finish
    }
}
