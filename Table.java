import java.io.FileWriter;
import java.io.IOException;

public class Table//most of the heavy lifting is done in the table class
{
    String name;
    String[] schema;//names of the columns
    String[] columnTypes;//types of the columns
    Row[] rows = new Row[0];//initializes to an empty row array, which will be appended on by the append method
    public Table(String[] n, String[] t, String na)//takes the names(schema), the types of the schema, and the table name
    {
        tableCreation(n,t);
        name = na;
    }
    public void tableCreation(String[] names, String[] types)//actual method to format the table
    {
        schema = new String[names.length];
        columnTypes = new String[names.length];
        for (int i=0;i<names.length;i++)//fills the schema and column types with their respective elements
        {
            schema[i] = names[i];
            columnTypes[i] = types[i];
        }
    }
    public void insert(String[] d)//appends a row to the bottom of the table
    {
        Row r = new Row(d,columnTypes);//constructs row
        rows = ArrayManipulator.rowAppend(rows,r);//appends row
    }
    public void print()
    {
        for(int i=0;i<schema.length;i++)//for loop to print the schema
        {
            System.out.print(schema[i]);
            if(i!=schema.length-1)
            {
                System.out.print(",");
            }
            else
            {
                System.out.print("\n");
            }
        }
        for(int i=0;i<columnTypes.length;i++)//for loop to print the schema types
        {
            System.out.print(columnTypes[i]);
            if(i!=columnTypes.length-1)
            {
                System.out.print(",");
            }
            else
            {
                System.out.print("\n");
            }
        }
        if(rows.length>0)//more printing is done only if there are rows to print
        {
            for (int i = 0; i < rows.length; i++)
            {
                rows[i].print();//utilizes row print method
            }
        }
    }
    public void store() throws IOException
    {
        FileWriter f = new FileWriter(name+".csv");//name of the written file is the table name with the .csv suffix
        for(int i=0;i<schema.length;i++)//this method is nearly identical to the print method, except it writes to a file instead of printing to the terminal
        {
            f.write(schema[i]);
            if(i!=schema.length-1)
            {
                f.write(",");
            }
            else
            {
                f.write("\n");
            }
        }
        if(rows.length>0)
        {
            for (int i = 0; i < rows.length; i++)
            {
                for(int j=0;j<rows[i].elements.length;j++)
                {
                    f.write(rows[i].elements[j].toString());
                    if(j!=rows[i].elements.length-1)
                    {
                        f.write(",");
                    }
                }
                f.write("\n");
            }
        }
        f.close();
    }
    public void select(String[] columns, String c)//takes in the specified columns to be printed, as well as the conditional
    {
        String[] conditional = c.split(" ");//splits conditional into right hand side, operator, and left hand side
        for(int i=0;i<columns.length;i++)//prints the specified column headers
        {
            System.out.print(columns[i]);
            if(i!=columns.length-1)
            {
                System.out.print(",");
            }
            else
            {
                System.out.print("\n");
            }
        }
        int[] typeIndices = new int[columns.length];//type indices array used to keep track of what types to be printed
        for(int i=0;i<columns.length;i++)
        {
            typeIndices[i] = ArrayManipulator.indexOf(schema, columns[i]);//checks the type index for each schema and stores in array
            System.out.print(columnTypes[ArrayManipulator.indexOf(schema, columns[i])]);//prints each column type
            if(i!=columns.length-1)//comma if its not the last element, new line otherwise
            {
                System.out.print(",");
            }
            else
            {
                System.out.print("\n");
            }
        }
        int index = ArrayManipulator.indexOf(schema,conditional[0]);//index(column) of the right hand side of the conditional
        String cType = columnTypes[index];//conditional type, uses the corresponding index and checks the column type
        if(cType.equalsIgnoreCase("String"))//all of these if statements and for loops are a variation on the same concept. each begins with checking the type of the conditional
        {
            if(conditional[1].equals("="))//next it checks the operator, each operator has its own if statement
            {
                for(int i=0;i<rows.length;i++)//next it goes through each row and checks the condition
                {
                    if(rows[i].elements[ArrayManipulator.indexOf(schema,conditional[0])].toString().equals(conditional[2]))//compares the left hand side element to the right hand side element using the specified operator
                    {
                        for(int j=0;j<columns.length;j++)//this for loop ensures that only the elements from the specified columns are printed
                        {
                            System.out.print(rows[i].elements[typeIndices[j]]);
                            if(j!=columns.length-1)
                            {
                                System.out.print(",");
                            }
                            else
                            {
                                System.out.print("\n");
                            }
                        }
                    }
                }
            }
            else if(conditional[1].equals("!="))//these are all essentially the same. it should be noted that the non-string types had to be casted for comparison
            {
                for(int i=0;i<rows.length;i++)
                {
                    if(!rows[i].elements[ArrayManipulator.indexOf(schema,conditional[0])].toString().equals(conditional[2]))
                    {
                        for(int j=0;j<columns.length;j++)
                        {
                            System.out.print(rows[i].elements[typeIndices[j]]);
                            if(j!=columns.length-1)
                            {
                                System.out.print(",");
                            }
                            else
                            {
                                System.out.print("\n");
                            }
                        }
                    }
                }
            }
        }
        else if(cType.equalsIgnoreCase("boolean"))
        {
            if(conditional[1].equals("="))
            {
                for(int i=0;i<rows.length;i++)
                {
                    if((boolean)(rows[i].elements[ArrayManipulator.indexOf(schema,conditional[0])])==Boolean.valueOf(conditional[2]))
                    {
                        for(int j=0;j<columns.length;j++)
                        {
                            System.out.print(rows[i].elements[typeIndices[j]]);
                            if(j!=columns.length-1)
                            {
                                System.out.print(",");
                            }
                            else
                            {
                                System.out.print("\n");
                            }
                        }
                    }
                }
            }
            if(conditional[1].equals("!="))
            {
                for(int i=0;i<rows.length;i++)
                {
                    if((boolean)(rows[i].elements[ArrayManipulator.indexOf(schema,conditional[0])])!=Boolean.valueOf(conditional[2]))
                    {
                        for(int j=0;j<columns.length;j++)
                        {
                            System.out.print(rows[i].elements[typeIndices[j]]);
                            if(j!=columns.length-1)
                            {
                                System.out.print(",");
                            }
                            else
                            {
                                System.out.print("\n");
                            }
                        }
                    }
                }
            }
        }
        else if(cType.equalsIgnoreCase("double"))
        {
            if(conditional[1].equals("="))
            {
                for(int i=0;i<rows.length;i++)
                {
                    if((double)(rows[i].elements[ArrayManipulator.indexOf(schema,conditional[0])])==Double.valueOf(conditional[2]))
                    {
                        for(int j=0;j<columns.length;j++)
                        {
                            System.out.print(rows[i].elements[typeIndices[j]]);
                            if(j!=columns.length-1)
                            {
                                System.out.print(",");
                            }
                            else
                            {
                                System.out.print("\n");
                            }
                        }
                    }
                }
            }
            else if(conditional[1].equals("!="))
            {
                for(int i=0;i<rows.length;i++)
                {
                    if((double)(rows[i].elements[ArrayManipulator.indexOf(schema,conditional[0])])!=Double.valueOf(conditional[2]))
                    {
                        for(int j=0;j<columns.length;j++)
                        {
                            System.out.print(rows[i].elements[typeIndices[j]]);
                            if(j!=columns.length-1)
                            {
                                System.out.print(",");
                            }
                            else
                            {
                                System.out.print("\n");
                            }
                        }
                    }
                }
            }
            else if(conditional[1].equals("<="))
            {
                for(int i=0;i<rows.length;i++)
                {
                    if((double)(rows[i].elements[ArrayManipulator.indexOf(schema,conditional[0])])<=Double.valueOf(conditional[2]))
                    {
                        for(int j=0;j<columns.length;j++)
                        {
                            System.out.print(rows[i].elements[typeIndices[j]]);
                            if(j!=columns.length-1)
                            {
                                System.out.print(",");
                            }
                            else
                            {
                                System.out.print("\n");
                            }
                        }
                    }
                }
            }
            else if(conditional[1].equals("<"))
            {
                for(int i=0;i<rows.length;i++)
                {
                    if((double)(rows[i].elements[ArrayManipulator.indexOf(schema,conditional[0])])<Double.valueOf(conditional[2]))
                    {
                        for(int j=0;j<columns.length;j++)
                        {
                            System.out.print(rows[i].elements[typeIndices[j]]);
                            if(j!=columns.length-1)
                            {
                                System.out.print(",");
                            }
                            else
                            {
                                System.out.print("\n");
                            }
                        }
                    }
                }
            }
            else if(conditional[1].equals(">="))
            {
                for(int i=0;i<rows.length;i++)
                {
                    if((double)(rows[i].elements[ArrayManipulator.indexOf(schema,conditional[0])])>=Double.valueOf(conditional[2]))
                    {
                        for(int j=0;j<columns.length;j++)
                        {
                            System.out.print(rows[i].elements[typeIndices[j]]);
                            if(j!=columns.length-1)
                            {
                                System.out.print(",");
                            }
                            else
                            {
                                System.out.print("\n");
                            }
                        }
                    }
                }
            }
            else if(conditional[1].equals(">"))
            {
                for(int i=0;i<rows.length;i++)
                {
                    if((double)(rows[i].elements[ArrayManipulator.indexOf(schema,conditional[0])])>Double.valueOf(conditional[2]))
                    {
                        for(int j=0;j<columns.length;j++)
                        {
                            System.out.print(rows[i].elements[typeIndices[j]]);
                            if(j!=columns.length-1)
                            {
                                System.out.print(",");
                            }
                            else
                            {
                                System.out.print("\n");
                            }
                        }
                    }
                }
            }
        }
        else if(cType.equalsIgnoreCase("int"))
        {
            if(conditional[1].equals("="))
            {
                for(int i=0;i<rows.length;i++)
                {
                    if((int)(rows[i].elements[ArrayManipulator.indexOf(schema,conditional[0])])==Integer.valueOf(conditional[2]))
                    {
                        for(int j=0;j<columns.length;j++)
                        {
                            System.out.print(rows[i].elements[typeIndices[j]]);
                            if(j!=columns.length-1)
                            {
                                System.out.print(",");
                            }
                            else
                            {
                                System.out.print("\n");
                            }
                        }
                    }
                }
            }
            else if(conditional[1].equals("!="))
            {
                for(int i=0;i<rows.length;i++)
                {
                    if((int)(rows[i].elements[ArrayManipulator.indexOf(schema,conditional[0])])!=Integer.valueOf(conditional[2]))
                    {
                        for(int j=0;j<columns.length;j++)
                        {
                            System.out.print(rows[i].elements[typeIndices[j]]);
                            if(j!=columns.length-1)
                            {
                                System.out.print(",");
                            }
                            else
                            {
                                System.out.print("\n");
                            }
                        }
                    }
                }
            }
            else if(conditional[1].equals(">="))
            {
                for(int i=0;i<rows.length;i++)
                {
                    if((int)(rows[i].elements[ArrayManipulator.indexOf(schema,conditional[0])])>=Integer.valueOf(conditional[2]))
                    {
                        for(int j=0;j<columns.length;j++)
                        {
                            System.out.print(rows[i].elements[typeIndices[j]]);
                            if(j!=columns.length-1)
                            {
                                System.out.print(",");
                            }
                            else
                            {
                                System.out.print("\n");
                            }
                        }
                    }
                }
            }
            else if(conditional[1].equals(">"))
            {
                for(int i=0;i<rows.length;i++)
                {
                    if((int)(rows[i].elements[ArrayManipulator.indexOf(schema,conditional[0])])>Integer.valueOf(conditional[2]))
                    {
                        for(int j=0;j<columns.length;j++)
                        {
                            System.out.print(rows[i].elements[typeIndices[j]]);
                            if(j!=columns.length-1)
                            {
                                System.out.print(",");
                            }
                            else
                            {
                                System.out.print("\n");
                            }
                        }
                    }
                }
            }
            else if(conditional[1].equals("<="))
            {
                for(int i=0;i<rows.length;i++)
                {
                    if((int)(rows[i].elements[ArrayManipulator.indexOf(schema,conditional[0])])<=Integer.valueOf(conditional[2]))
                    {
                        for(int j=0;j<columns.length;j++)
                        {
                            System.out.print(rows[i].elements[typeIndices[j]]);
                            if(j!=columns.length-1)
                            {
                                System.out.print(",");
                            }
                            else
                            {
                                System.out.print("\n");
                            }
                        }
                    }
                }
            }
            else if(conditional[1].equals("<"))
            {
                for(int i=0;i<rows.length;i++)
                {
                    if((int)(rows[i].elements[ArrayManipulator.indexOf(schema,conditional[0])])<Integer.valueOf(conditional[2]))
                    {
                        for(int j=0;j<columns.length;j++)
                        {
                            System.out.print(rows[i].elements[typeIndices[j]]);
                            if(j!=columns.length-1)
                            {
                                System.out.print(",");
                            }
                            else
                            {
                                System.out.print("\n");
                            }
                        }
                    }
                }
            }
        }
    }
}
