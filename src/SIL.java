//Suyash Shashank Deshmukh
//CSULB ID:016105854
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;

class SymbolTable{	
	String name;
	int value;
	String type;
	public static int count=0;
	static SymbolTable[] Table= new SymbolTable[50];

	public void updateValue(String idname, int idvalue)
	{
		int i=0;
		i=this.findIndex(idname);
		if(i==-1)
		{
			System.err.println("Undefined Variable "+idname);
			System.exit(0);
		}
		Table[i].value=idvalue;

	}

	public int findIndex(String idname)
	{
		int i=0;
		while(i<count)
		{	
			if(Table[i].name.equals(idname))
				return i;
			i++;
		}
		return -1;
	}
	public void addSymbol(String token,String type)
	{
		if(findIndex(token)==-1)
		{
			Table[count] = new SymbolTable();
			Table[count].name=token;
			Table[count].type=type;
			Table[count].value=-1;
			count++;
		}
		else
		{
			System.err.println("Duplicate Variable "+token);
			System.exit(0);
		}
	}
	public void printTable()
	{
		int i=0;
		System.out.println("Name\tValue\tType");
		while(i<count)
		{
			System.out.println(Table[i].name+"\t"+Table[i].value+"\t"+Table[i].type);
			i++;
		}
	}

	public void readInput(String[] key)
	{
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String line;
			line = br.readLine();

			String[] values=line.split(" ");
			String[] key1=new String[50];
			int k=0;
			for (String string : key) {
				if(string==null)
					break;
				if(string.equals(","))
					continue;
				else
				{
					key1[k]=string;
					k++;
				}
			}
			
			for(int i=0;i<key1.length && key1[i]!=null;i++)
			{

				if(key1[i].equals(","))
				{
					continue;
				}
				if(findIndex(key1[i])==-1)
				{
					System.err.println("Undefined Variable "+key1[i]);
					System.exit(0);
				}
				updateValue(key1[i], Integer.parseInt(values[i]));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Invalid Input Entered!!");
			System.exit(0);
		}
		catch(NumberFormatException n)
		{
			System.err.println("Entered Value is out of range!!");
			System.exit(0);
		}

	}

	public int getValue(String operand) {
		// TODO Auto-generated method stub
		int index = findIndex(operand);
		if(index==-1)
		{
			System.err.println("Undefined Variable "+operand);
			System.exit(0);
		}
		if(Table[index].value==-1)
		{
			System.err.println("Variable "+operand+" not initialized!");
			System.exit(0);
		}
		
		return Table[index].value;
	}

	public int evaluate(String infix) {
		
		char[] bits = infix.toCharArray();

		Stack<Integer> values = new Stack<Integer>();	
		Stack<Character> symbols = new Stack<Character>();
		try
		{
		if(infix.matches("[\\(*0-9\\)*]+([+\\-()/*][0-9]*)*")==false)
		{
			System.err.println("Invalid Expression Format");
			System.exit(0);
		}
	

		for (int i = 0; i < bits.length; i++)
		{
			if (bits[i] == ' ')						
				continue;

			if (Character.isDigit(bits[i]))		
			{
				StringBuffer buffer = new StringBuffer();
				while (i < bits.length && Character.isDigit(bits[i]))	
				{ 
					buffer.append(bits[i]);
					i++;
				}
				i--;
				values.push(Integer.parseInt(buffer.toString()));
			}

			else if (bits[i] == '(' && i < bits.length)			
				symbols.push(bits[i]);

			else if (bits[i] == ')' && i < bits.length)			
			{
				while (symbols.peek() != '(')
					values.push(applyOp(symbols.pop(), values.pop(), values.pop()));
				symbols.pop();
			}

			else if (bits[i] == '+' || bits[i] == '-' ||		
					bits[i] == '*' || bits[i] == '/' && i < bits.length)
			{
				while (!symbols.empty() && hasPrecedence(bits[i], symbols.peek()))
					values.push(applyOp(symbols.pop(), values.pop(), values.pop()));				
				symbols.push(bits[i]);															

			}
		}

		while (!symbols.empty())													
			values.push(applyOp(symbols.pop(), values.pop(), values.pop()));		

		// Top of 'values' contains result, return it
		return values.pop();
		}
		catch(Exception E)
		{
			System.err.println("Invalid Expression Format!!");
			System.exit(0);
		}
		return values.pop();
	}

	public static boolean hasPrecedence(char op1, char op2)				
	{																	
		if (op2 == '(' || op2 == ')')
			return false;
		if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
			return false;
		else
			return true;
	}

	public static int applyOp(char op, int b, int a)			
	{															
		switch (op)
		{
		case '+':
			return a + b;
		case '-':
			return a - b;
		case '*':
			return a * b;
		case '/':
			if (b == 0)
				throw new
				UnsupportedOperationException("Cannot divide by zero");
			return a / b;
		}
		return 0;

	}

	public boolean isNumeric(String input) {
		// TODO Auto-generated method stub
		for (char chararray : input.toCharArray())
		{
			if (!Character.isDigit(chararray)) return false;
		}
		return true;

	}

	public int getKeyFromValue(HashMap<Integer, String> h1, String string) {
		// TODO Auto-generated method stub
		for(int o :h1.keySet())
		{
			if(h1.get(o).equals(string))
				return o;
		}
		return -1;
	}

	public void keywordInteger(String[] tokenlist) {
		// TODO Auto-generated method stub
		for(int c=1;c<tokenlist.length && tokenlist[c]!=null;c++)
		{	
			String a= tokenlist[c];
			if(a.equals(" ")||a.equals(","))
			{											// Added Integer Code---- 11 NOV
				continue;
			}
			addSymbol(a,"integer");
		}
	}

	public void keywordLet(String[] tokenlist) {
		// TODO Auto-generated method stub
		if(tokenlist.length>4)
			{

				String s="";
				for(int j=3;j<tokenlist.length && tokenlist[j]!=null;j++)
				{
					
					s=s.concat(tokenlist[j]);
				}
				StringTokenizer st1=new StringTokenizer(s,"[()+\\-*/]",true);
				String exp="";
				if(!st1.hasMoreTokens())
				{
					System.err.println("Syntax Error in LET statement!");
					System.exit(0);
				}
				String a3=st1.nextToken();

				while(a3!=null)
				{

					//a2=a1.toCharArray();
					if(a3.equals("+")||a3.equals("-")||a3.equals("*")||a3.equals("/")||a3.equals("(")||a3.equals(")"))
					{
						exp=exp.concat(a3.toString());
						if(st1.hasMoreTokens())
							a3=st1.nextToken();
						else
							a3=null;
						continue;
					}
					else if(!isNumeric(a3))
					{
						int ind= findIndex(a3);
						if(ind==-1 || Table[ind].value==-1 )
						{
							System.err.println("\nVariable "+a3+" not initialized");
							System.exit(0);
						}
						exp=exp.concat(""+Table[ind].value);
					}
					else
					{
						exp=exp.concat(a3);
						a3=null;

					}
					if(st1.hasMoreElements())
						a3=st1.nextToken();
					else
						a3=null;
				}

										
				int result1=evaluate(exp);	
				updateValue(tokenlist[1],result1);

			}
			else
			{

				String target=tokenlist[1];
				//String operator= st.nextToken();
				if(findIndex(target)!=-1)
				{
					updateValue(target,Integer.parseInt(tokenlist[3]));
				}
				else
				{
					System.err.println("Undefined Target Variable "+target);
					System.exit(0);
				}

			}												//Added LET Code - 11 NOV

		
	}

	public void keywordPrint(String[] tokenlist) {
		// TODO Auto-generated method stub
		String l1="";
		String x1="";
		int flag1=1;
		if(tokenlist[1].equals(","))
		{
			System.err.println("Syntax error! Check Syntax for "+tokenlist[0]+" statement");
			System.exit(0);
		}
		for(int a=1;a<tokenlist.length && tokenlist[a]!=null;a++)
		{
			if(tokenlist[a].charAt(0)=='"')
			{
				if(flag1==0)
				{
					System.err.println("Invalid syntax , missing!");
					System.exit(0);
				}
				System.out.print(tokenlist[a].substring(1, tokenlist[a].length()-1));
				flag1=0;
			}
			else if(tokenlist[a].equals(","))
			{
				flag1=1;
				continue;
			}
			else
			{
				if(flag1==0)
				{
					System.err.println("Invalid syntax , missing!");
					System.exit(0);
				}
				int count=0;
				while(tokenlist[a+1]!=null/* && !tokenlist[a+1].equals(",")*/ )
				{
					//flag=0;
					if(!tokenlist[a+1].equals(","))
					{	
						if(tokenlist[a+1].charAt(0)=='"' /*&& flag1==0*/)
						{
							System.err.println("Invalid syntax , missing!");
							System.exit(0);
						}
							

						if(isNumeric(tokenlist[a]) || tokenlist[a].equals("+") || tokenlist[a].equals("-")
								|| tokenlist[a].equals("*") || tokenlist[a].equals("/") || tokenlist[a].equals("(")
								|| tokenlist[a].equals(")"))
						{	x1=x1.concat(tokenlist[a]);
						count++;
						a++;
						}
						else
						{
							String x2=""+getValue(tokenlist[a]);
							x1=x1.concat(x2);
							a++;
						}
					}
					else
					{
						if(isNumeric(tokenlist[a]))
							if(x1.equals(""))
								x1=tokenlist[a];
							else
								x1=x1.concat(tokenlist[a]);
						else
						{
							String x3=""+getValue(tokenlist[a]);			
							if(x1.equals(""))
								x1=x3;
							else
								x1=x1.concat(x3);
						}
						if(tokenlist[a+1].equals(","))
						{
							flag1=1;
							break;
						}
					}

				}
				if(tokenlist[a+1]==null)
				{
					if( tokenlist[a].equals("+") || tokenlist[a].equals("-")
							|| tokenlist[a].equals("*") || tokenlist[a].equals("/") || tokenlist[a].equals("(")
							|| tokenlist[a].equals(")"))
					{
						System.err.println("Invalid Statement! Cannot terminate with an operator!!");
						System.exit(0);
					}

					if(isNumeric(tokenlist[a]))
						if(x1.equals(""))
							x1=tokenlist[a];
						else
							x1=x1.concat(tokenlist[a]);
					else
					{
						String x3=""+getValue(tokenlist[a]);			
						if(x1.equals(""))
							x1=x3;
						else
							x1=x1.concat(x3);
					}
				}
				if(count==0)
				{
					System.out.print(x1);
					x1="";
					flag1=0;
				}
				else
				{
					int result= evaluate(x1);
					System.out.print(result);
					x1="";
					flag1=0;
				}
			}

		}
		//System.out.println();
		if(flag1==1)
		{
			System.err.println("Syntax Error in print statement");			//Added Print Code - 11 NOV
			System.exit(0);
		}

		
	}


	public void keywordPrintln(String[] tokenlist) {
		// TODO Auto-generated method stub
		String l="";
		String s1="";
		int flag=1;
		if(tokenlist[1].equals(","))
		{
			System.err.println("Syntax error! Check Syntax for "+tokenlist[0]+" statement");
			System.exit(0);
			
		}
		for(int a=1;a<tokenlist.length && tokenlist[a]!=null;a++)
		{
			if(tokenlist[a].charAt(0)=='"')
			{
				if(flag==0)
				{
					System.err.println("Invalid syntax , missing!");
					System.exit(0);
				}
				System.out.print(tokenlist[a].substring(1, tokenlist[a].length()-1));
				flag=0;
			}
			else if(tokenlist[a].equals(","))
			{
				flag=1;
				continue;
			}
			else
			{
				if(flag==0)
				{
					System.err.println("Invalid syntax , missing!");
					System.exit(0);
				}
				int count=0;
				while(tokenlist[a+1]!=null/* && !tokenlist[a+1].equals(",")*/ )
				{
					//flag=0;
					if(!tokenlist[a+1].equals(","))
					{	
						if(tokenlist[a+1].charAt(0)=='"' /*&& flag==0*/ )
						{
							System.err.println("Invalid syntax , missing!");
							System.exit(0);
						}
//			
						if(isNumeric(tokenlist[a]) || tokenlist[a].equals("+") || tokenlist[a].equals("-")
								|| tokenlist[a].equals("*") || tokenlist[a].equals("/") || tokenlist[a].equals("(")
								|| tokenlist[a].equals(")"))
						{	s1=s1.concat(tokenlist[a]);
						count++;
						a++;
						}
						else
						{
							String s2=""+getValue(tokenlist[a]);
							s1=s1.concat(s2);
							a++;
						}
					}
					else
					{
						if(isNumeric(tokenlist[a]))
							if(s1.equals(""))
								s1=tokenlist[a];
							else
								s1=s1.concat(tokenlist[a]);
						else if(tokenlist[a].equals("+") || tokenlist[a].equals("-")
								|| tokenlist[a].equals("*") || tokenlist[a].equals("/") || tokenlist[a].equals("(")
								)
						{	
							System.err.println("Cannot end an expression with an operator!!!");
							System.exit(0);
						}
						else if(tokenlist[a].equals(")"))
						{
							if(s1.equals(""))
							{
								System.err.println("Dangling closing bracket!!");
								System.exit(0);
							}
							else
							{
								s1=s1.concat(tokenlist[a]);
							}
						}
						else
						{
							String s3=""+getValue(tokenlist[a]);			
							if(s1.equals(""))
								s1=s3;
							else
								s1=s1.concat(s3);
						}
						if(tokenlist[a+1].equals(","))
						{
							flag=1;
							break;
						}
					}

				}
				if(tokenlist[a+1]==null)
				{
					if( tokenlist[a].equals("+") || tokenlist[a].equals("-")
							|| tokenlist[a].equals("*") || tokenlist[a].equals("/") || tokenlist[a].equals("(")
							|| tokenlist[a].equals(")"))
					{
						System.err.println("Invalid Statement! Cannot terminate with an operator!!");
						System.exit(0);
					}
					if(isNumeric(tokenlist[a]))
						if(s1.equals(""))
							s1=tokenlist[a];
						else
							s1=s1.concat(tokenlist[a]);
					else
					{
						String s3=""+getValue(tokenlist[a]);			
						if(s1.equals(""))
							s1=s3;
						else
							s1=s1.concat(s3);
					}
				}
				if(count==0)
				{
					System.out.print(s1);
					s1="";
					flag=0;
				}
				else
				{
					int result= evaluate(s1);
					System.out.print(result);
					s1="";
					flag=0;
				}
			}

		}
		System.out.println();
		if(flag==1)
		{
			System.err.println("Syntax Error in println statement");			//Added PRINTLN - 11 NOV
			System.exit(0);
		}
		
	}

	public void keywordInput(String[] tokenlist) {
		// TODO Auto-generated method stub
		String[] c1= new String[50];
		int count=0;
		for(int c=1;c<tokenlist.length && tokenlist[c]!=null;c++)
		{	
			c1[count]=tokenlist[c].trim();
			count++;
		}
		readInput(c1);		//Added INPUT code - 11 NOV
		
	}

	public int keywordGoto(String[] tokenlist,int number, Stack stack, HashMap h1) {
		// TODO Auto-generated method stub
		if(tokenlist[1].contains(".")|| !isNumeric(tokenlist[1]))
		{
			System.err.println("Invalid GOTO target!");
			System.exit(0);
		}	
		int line_num=Integer.parseInt(tokenlist[1]);
		stack.push(number++);
		number=getKeyFromValue(h1,tokenlist[1]);
		if(number==-1)
		{
			System.err.println("Invalid GOTO target!");
			System.exit(0);
		}
		return number;
		
	}


}


public class SIL {
	static FileReader fr = null;
	static HashMap<Integer,String> h1 = new HashMap<Integer,String>();
	static HashMap<String,String> h2 = new HashMap<String,String>();
	static Stack<Integer> stack = new Stack();
	public static int total_number_lines=0;
	
	public static int countOccurrences(String line1, String string) {
		// TODO Auto-generated method stub
		int count = 0;
		while (line1.indexOf(string)>-1){
		    line1 = line1.replaceFirst(string, "");
		    count++;
		}
		return count ;

	}

	public String[] tokenize(String line)
	{
		String[] tokens= new String[100];
		int k=0;
		int number=1;
		int exit=0;
		String regex = "[(\".*\")=><()+\\-*/%,!\\|\\&\\[\\]\\{\\}\\# ]";
		StringTokenizer st = new StringTokenizer(line,regex,true); 
		String token1=null;
		int flag=0;
		int count=0;
		while(st.hasMoreTokens())
		{
			token1 = st.nextToken();
			if(token1.equals(" "))
			{
				//st.nextToken();
				continue;
			}
			for(int j=1;j<token1.length();j++)
			{
				if(Character.isDigit(token1.charAt(j)))
				{
					count++;
					flag=1;					
				}
			}
			if(count==token1.length()-1)
			{
				count=0;
				flag=0;
			}
			if(token1.length()>32 /*|| flag==1*/)
			{
				flag=0;
				continue;
			}
			if(token1.charAt(0)==' ')
				token1=token1.trim();

			if(token1.equals("\""))
			{
				String token2="";
				String s=token1;
				do
				{
					//System.out.println("\""+st.nextToken()+"\"");
					s=s.concat(token2);
					token2=st.nextToken();
				}while(!token2.equals("\""));
				tokens[k]=s.concat("\"");
				k++;
			}
			else
			{
				tokens[k]=token1;
				k++;
			}
		}
		return tokens;

	}






	public BufferedReader readFile(String Filepath) throws FileNotFoundException      // Function to open input file  
	{										  										// and return BufferedReader object.
		FileReader fr = new FileReader(Filepath);
		BufferedReader br = new BufferedReader(fr);
		return br;
	}

	public boolean isDigit(String operand)
	{
		int count=0;
		for(int j=0;j<operand.length();j++)
		{
			if(Character.isDigit(operand.charAt(j)))
			{
				count++;					
			}
		}
		if(count==operand.length())
			return true;
		return false;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			int exit=0;
			SymbolTable symtable= new SymbolTable();
			SIL lex1 = new SIL();
			BufferedReader br=lex1.readFile("C:\\abc.txt");
			String line1 = br.readLine();
			int number=0;
			while(line1!=null)
			{
				
				if(line1.contains("  "))
					line1=line1.replaceFirst("  "," ");
				if(line1.contains("\t"))
					line1=line1.replaceFirst("\t"," ");
				String[] line_split=line1.split(" ",2);
				
				number++;
				h1.put(number, line_split[0]);
				h2.put(line_split[0],line_split[1]);
				line1 = br.readLine();
			}
			String[] tokens= new String[20];
			int k=0;
			total_number_lines=number;
			number=1;
			//int counter=1;
			int if_condition=0;
			String[] tokenlist=null;
			String line=null;
			while(number<=total_number_lines)						  // Loop to read input line by line.
			{
				if(if_condition==0)
				{
				
					if (exit == 1)
						System.exit(0);

					String line_number = h1.get(number);
					line = h2.get(line_number);
					tokenlist = null;
					try {
						tokenlist = lex1.tokenize(line);
					} catch (Exception e) {
						System.err.println("Invalid Syntax! Check Grammar!");
						System.exit(0);
					}
					if (line.substring(0, 1).equals("//"))
						continue;
				}
				
				if (tokenlist[0].matches(
						"(INTEGER|integer|PRINT|print|PRINTLN|println|LET|let|END|end|INPUT|input|GOTO|goto|GOSUB|gosub|RET|ret|if|IF)"))
					tokenlist[0] = tokenlist[0].toUpperCase();

				// if(!tokenlis.equals(","))
				// {
				
				//if_condition=0;
				switch(tokenlist[0])

				{
				case "INTEGER" :
					if(if_condition==0)
					{
						if (line.matches(
								"(INTEGER|integer)[ ]*[aA-zZ][a-z|A-Z|0-9|_]*[ ]*(,[ ]*[aA-zZ][a-z|A-Z|0-9|_]*[ ]*)*") == false)

						{
							System.err.println("Syntax Error in Integer Statement!");
							System.exit(0);
						}
					}	// while(st.hasMoreTokens())
					symtable.keywordInteger(tokenlist);
					if_condition=0;
					/*
					 * for(int c=1;c<tokenlist.length && tokenlist[c]!=null;c++)
					 * { String a= tokenlist[c]; if(a.equals(" "
					 * )||a.equals(",")) { // Commented Integer Code---- 11 NOV
					 * continue; } symtable.addSymbol(a,"integer"); }
					 */

					break;

				case "LET" :
					if(if_condition==0)
					{
						if (line.matches("(LET|let)[ ]*[aA-zZ][a-z|A-Z|0-9|_ ]*[ ]*(=).*") == false)
						// if(line.matches("(LET|let)
						// [aA-zZ][a-z|A-Z|0-9|_]*(=)[aA0-9-zZ][a-z|A-Z|0-9]+[[+|-|/|*|(|)]*[[aA0-9-zZ][a-z|A-Z|0-9]]]*")==false)
						{
							System.err.println("Syntax Error in LET Statement!");
							System.exit(0);
						}
					}
					symtable.keywordLet(tokenlist);
					if_condition=0;
					
					break;

				case "PRINT":
					symtable.keywordPrint(tokenlist);
					if_condition=0;
					break;


				case "PRINTLN":
					symtable.keywordPrintln(tokenlist);
					if_condition=0;
					break;
				
				case "INPUT":
					//String c= st.nextToken();
					if(if_condition==0)
					{
						if (line.matches(
								"(INPUT|input)[ ]*[aA-zZ][a-z|A-Z|0-9|_]*[ ]*(,[ ]*[ aA-zZ][a-z|A-Z|0-9|_]*[ ]*)*") == false) {
							System.err.println("Syntax Error in Input Statement!");
							System.exit(0);
						}
					}

					symtable.keywordInput(tokenlist);
					if_condition=0;
					break;


				case "END":
					System.out.println("End of Program!");
					exit=1;
					break;
					
				case  "GOTO":
					if(if_condition==0)
					{
						if (line.matches("(GOTO|goto)[ ]*[0-9]+") == false) {
							System.err.println("Syntax Error in Goto Statement!");
							System.exit(0);
						}
					}
					number=symtable.keywordGoto(tokenlist,number,stack,h1);
					if_condition=0;
					continue;
					
				case "GOSUB":
					if(if_condition==0)
					{
						if (line.matches("(GOSUB|gosub)[ ]*[0-9]+") == false) {
							System.err.println("Syntax Error in Goto Statement!");
							System.exit(0);
						}
					}
					int line_no=Integer.parseInt(tokenlist[1]);
					number++;
					stack.push(number);
					number=symtable.getKeyFromValue(h1,tokenlist[1]);
					if(number==-1)
					{
						System.err.println("Invalid GOTO target!");
						System.exit(0);
					}
					if_condition=0;
					continue;
					
				case "RET":
					try{
						number=stack.pop();
					}catch(EmptyStackException e)
					{
						System.err.println("Invalid Return Statement!");
						System.exit(0);
					}
					if_condition=0;
						continue;
						
				case "IF":
					int counter=1;
					String exp1="";
					int count =countOccurrences(line.toLowerCase(),"if"); 
					if(count>1)
					{
						System.err.println("Syntax Error!");
						System.exit(0);
					}
					count=countOccurrences(line.toLowerCase(),"then");
					if(count>1)
					{
						System.err.println("Syntax Error!");
						System.exit(0);
					}
					if(!line.toLowerCase().contains("then".toLowerCase()))
					{
						System.err.println("THEN keyword not present! Syntax Error!");
						System.exit(0);
					}
					while(!tokenlist[counter].equals("=") && !tokenlist[counter].equals("<") && !tokenlist[counter].equals(">"))
					{
						if(tokenlist[counter].equalsIgnoreCase("then"))
						{
							System.err.println("Expression Missing!");
							System.exit(0);
						}
						if(symtable.isNumeric(tokenlist[counter])||tokenlist[counter].equals("+") || tokenlist[counter].equals("-")
								|| tokenlist[counter].equals("*") || tokenlist[counter].equals("/") || tokenlist[counter].equals("(")
								|| tokenlist[counter].equals(")"))
							exp1=exp1.concat(tokenlist[counter]);
						else
						{
							exp1=exp1.concat(""+symtable.getValue(tokenlist[counter]));
						}
						
						counter++;
					}
					int lvalue= symtable.evaluate(exp1);
					String Operation=tokenlist[counter];
					counter++;
					String exp2="";
					while(!tokenlist[counter].toUpperCase().equals("THEN"))
					{
						if(tokenlist[counter].equals("=") || tokenlist[counter].equals("<") || tokenlist[counter].equals(">"))
						{
							System.err.println("Invalid Expression!");
							System.exit(0);
						}
						if(symtable.isNumeric(tokenlist[counter])||tokenlist[counter].equals("+") || tokenlist[counter].equals("-")
								|| tokenlist[counter].equals("*") || tokenlist[counter].equals("/") || tokenlist[counter].equals("(")
								|| tokenlist[counter].equals(")"))
							exp2=exp2.concat(tokenlist[counter]);
						else
						{
							exp2=exp2.concat(""+symtable.getValue(tokenlist[counter]));
						}
						
						counter++;
					}
					int rvalue= symtable.evaluate(exp2);
					boolean result=true;
					switch(Operation)
					{
					case "=":
						if(lvalue==rvalue)
							result=true;
						else
							result=false;
						break;
					case ">":
						if(lvalue>rvalue)
							result=true;
						else
							result=false;
						break;
					case "<":
						if(lvalue<rvalue)
							result=true;
						else
							result=false;
						break;
					}
					if(result==true)
					{
						counter++;
						int index=0;
						String keyword=tokenlist[counter];
						if(keyword==null)
						{
							System.err.println("Statement Missing! Check Syntax!");
							System.exit(0);
						}
						String[] tokenarray=null;
						while(!(tokenlist[counter]==null))
						{
							tokenlist[index]=tokenlist[counter];
							tokenlist[counter]=null;
							index++;
							counter++;
						}
						while(!(tokenlist[index]==null))
						{
							tokenlist[index]=null;
							index++;
						}
						if(if_condition==0)
						{
							if_condition=1;
							
						}
						else
							if_condition=0;
						
						continue;
						
					}
					break;
						
				default:
				
					System.err.print("Invalid Keyword");
					System.exit(0);

				}
				number++;


			}
			//}
			
			//symtable.printTable();


		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Please Enter Valid Filepath");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IOException Caught.");
			e.printStackTrace();
		}	

	}


}
