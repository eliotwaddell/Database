public class ArrayManipulator//basically every method that i created to mutate arrays since arraylists were not allowed
{
    public ArrayManipulator()
    {

    }
    public static Object[] objectExpansion(Object[] a)//this is a method used to expand an array by one. only used as part of the append method
    {
        Object[] newArray = new Object[a.length+1];
        for(int i=0;i<a.length;i++)//copies array with an additional empty space to be filled
        {
            newArray[i]=a[i];
        }
        a=newArray;
        return newArray;
    }
    public static Object[] objectAppend(Object[] a, Object n)//expands an array and adds element 'n'
    {
        a = objectExpansion(a);
        a[a.length-1]=n;
        return a;
    }
    public static String[] stringExpansion(String[] a)//these methods are the exact same except there is one for every type of array
    {
        String[] newArray = new String[a.length+1];
        for(int i=0;i<a.length;i++)
        {
            newArray[i]=a[i];
        }
        a=newArray;
        return newArray;
    }
    public static String[] stringAppend(String[] a, String n)
    {
        a = stringExpansion(a);
        a[a.length-1]=n;
        return a;
    }
    public static Table[] tableExpansion(Table[] a)
    {
        Table[] newArray = new Table[a.length+1];
        for(int i=0;i<a.length;i++)
        {
            newArray[i]=a[i];
        }
        a=newArray;
        return newArray;
    }
    public static Table[] tableAppend(Table[] a, Table n)
    {
        a = tableExpansion(a);
        a[a.length-1]=n;
        return a;
    }
    public static Row[] rowExpansion(Row[] a)
    {
        Row[] newArray = new Row[a.length+1];
        for(int i=0;i<a.length;i++)
        {
            newArray[i]=a[i];
        }
        a=newArray;
        return newArray;
    }
    public static Row[] rowAppend(Row[] a, Row n)
    {
        a = rowExpansion(a);
        a[a.length-1]=n;
        return a;
    }
    public static boolean contains(String[] a, String n)//contains method used to ensure that the table name is unique
    {
        for(int i=0;i<a.length;i++)//checks every element in a string array, returns true if any element matches
        {
            if(a[i].equalsIgnoreCase(n))
            {
                return true;
            }
        }
        return false;
    }
    public static String unique(String[] a)//used to ensure array has no duplicate elements, used when creating a table
    {
        for(int i=0;i<a.length;i++)//checks every element against every other element
        {
            for(int j=i+1;j<a.length;j++)
            {
                if(a[i].equalsIgnoreCase(a[j]))
                {
                    return a[j];
                }
            }
        }
        return "";
    }
    public static String validTypes(String[] a)//checks if the array of types is valid, aka one of the four accepted types
    {
        for(int i=0;i<a.length;i++)
        {
            if(!(a[i].equalsIgnoreCase("int")||a[i].equalsIgnoreCase("boolean")||a[i].equalsIgnoreCase("double")||a[i].equalsIgnoreCase("String")))
            {
                return a[i];
            }
        }
        return "";
    }
    public static int indexOf(String[] a, String e)//used to find the index of an element, given the element is in the array
    {
        for(int i=0;i<a.length;i++)//for loop iterates until index is returned
        {
            if(a[i].equalsIgnoreCase(e))
            {
                return i;
            }
        }
        return -1;//if index is not found, returns -1
    }
}
