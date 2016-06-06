import java.util.*;
public class Compiler {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String fileName;
		if(args.length > 0){
			fileName = args[0];
		}
		else{
			out("input file to compile");
			fileName = in.next();
		}
		Lexical l = new Lexical(fileName);
		l.close();
		l.getTokens();
		l.showAllTokens();
		SyntaxTree st = new SyntaxTree(l.tokenSet);
		System.out.println("Syntax Completed");
		System.out.println(st.isCorrect);
		for(int i = 0 ; i < st.localList.size() ; i++){
		System.out.println(st.localList.get(i).name + "   " + st.localList.get(i).type + "  " + st.localList.get(i).scope);
		}
		for(int i = 0 ; i < st.classList.size() ; i++){
			System.out.println(st.classList.get(i).show());
			}
		System.out.println(st.Error);
		
		//l.makeTokens();
		/*l.toChars();*/
			

	}
	static void out(Object o){
		System.out.println(o);
	}

}
