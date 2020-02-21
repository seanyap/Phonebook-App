import java.util.*;
import java.io.*;

class PhonebookApp
{
    public static void main(String[] args) throws IOException
    {
        if (args.length > 0)
        {
            String fileName = args[0];
            Phonebook pb = new Phonebook(fileName);
            Scanner kybd = new Scanner(System.in);
            System.out.print("lookup, quit (l/q)? ");
            while (kybd.hasNext())
            {
                char input = kybd.next().charAt(0);
                if (input == 'l')
                {
                    System.out.print("last name? ");
                    String last = kybd.next();
                    System.out.print("first name? ");
                    String first = kybd.next();
                    PhonebookEntry result = pb.lookup(last, first);
                    if (result != null) { System.out.println(result.toString() + "\n"); }
                    else { System.out.println("-- Name not found\n"); } 
                }
                else if (input == 'q') { break; }
                System.out.print("lookup, quit (l/q)? ");
            }
        }
        else { System.out.println("Usage: PhonebookApp 'phonebook-filename'"); }
    }
}

class Phonebook
{
    // Behaviors
    public Phonebook(String fileName) throws IOException
    {
        Scanner in = new Scanner(new File(fileName));
        while (in.hasNext())
        {
            PhonebookEntry entry = PhonebookEntry.read(in);
            Name name = entry.getName();
            tMap.put(name, entry);
        }
    }
    public PhonebookEntry lookup(String last, String first)
    {
       Name name = new Name(last, first);
       return tMap.get(name);
    }
    // Fields
    private Map <Name, PhonebookEntry> tMap = new TreeMap <Name, PhonebookEntry>();
}

class PhonebookEntry
{
    // Behaviors
    public PhonebookEntry(Name name, ArrayList<ExtendedPhoneNumber> numbers)
    {
        this.name = name;
        this.numbers = numbers;
    }
    public static PhonebookEntry read(Scanner in) throws IOException
    {
        String last = in.next();
        String first = in.next();
        int numCount = in.nextInt();
        Name name = new Name(last, first);
        ArrayList<ExtendedPhoneNumber> numbers = new ArrayList<ExtendedPhoneNumber>(numCount);
        for (int i=0; i<numCount; i++)
        {
          ExtendedPhoneNumber temp = ExtendedPhoneNumber.read(in);
          numbers.add(temp);
        }
        return new PhonebookEntry(name, numbers);
    }
    public Name getName() { return name; }
    public String getPhoneNumbers()
    {
        int count = 1;
        String totalNumInStr = "";
        for (ExtendedPhoneNumber num : numbers) { String numInStr = num.toString(); if (count < numbers.size()) { totalNumInStr += numInStr + ", "; } else { totalNumInStr += numInStr; } count++; }
        return totalNumInStr;
    }
    public String toString() { return name.toString() + "'s phone numbers: [" + getPhoneNumbers() + "]"; }
    // Fields
    private Name name;
    private ArrayList<ExtendedPhoneNumber> numbers;
}

class Name implements Comparable<Name>
{
	// Constructors
	public Name() { this("", ""); }
	public Name(String last, String first) { this.last = last; this.first = first; }
	// Behaviors;
	public static Name read(Scanner sc) throws IOException
	{
		String last = sc.next();
		String first = sc.next();
		return new Name(last, first);
	}
	public String toString() { return first + " " + last; }
	public boolean equals(Name other)
	{
		return last.equals(other.last) && first.equals(other.first);
	}
	public int compareTo(Name other)
	{
	    Name name = new Name(last, first);
	    return name.toString().compareTo(other.toString());
	}
	// States
	private String last;
	private String first;
}

class PhoneNumber
{
	// Constructors
	public PhoneNumber() { this(""); }
	public PhoneNumber(String num) { this.num = num; }

	// Behaviors
	public static PhoneNumber read(Scanner sc) throws IOException
	{
		String number = sc.next();
		return new PhoneNumber(number);
	}
	public String toString() { return num; }
	public boolean equals(PhoneNumber other)
	{
		return num.equals(other.num);
	}
	// States
	protected String num;
}

class ExtendedPhoneNumber extends PhoneNumber
{
    // Behaviors
    public ExtendedPhoneNumber(String desc, String num)
    {
        super(num);
        this.desc = desc;
    }
    public static ExtendedPhoneNumber read(Scanner in) throws IOException
    {
        String desc = in.next();
        String num = in.next();
        return new ExtendedPhoneNumber(desc, num);
    }
    public String toString() { return desc + ": " + num; }
    // Fields
    private String desc;
}

