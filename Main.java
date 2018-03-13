/*For testing purposes we will be using the directory: C:\Users\vanes\Desktop\Directory*/
import java.nio.file.*;
import java.io.File;
import java.io.FilenameFilter;
import java.util.*;
import java.io.*;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;


public class Main {
	public static void main (String[] args) throws FileNotFoundException {
	/*	for (String s: args) {
			System.out.println(s);
		}*/
			Path path = Paths.get(args[0]);
			System.out.println("Path: "+ path);

			String textPath = args[0];
			System.out.println("textPath:"+textPath);

			String type = args[1];
			System.out.println("Type: "+type);

			Main m = new Main();
			m.listFiles(textPath);

			File[] files = m.finder(textPath);

			//Prints out all the java files
			for (File f: files){
					System.out.println("Java Files: "+ f.getName());


					String fileInString = f.toString();

					Scanner scanner = new Scanner(new File(fileInString));
					String theString = "";

					theString = scanner.nextLine();
					while (scanner.hasNextLine()) {
						theString = theString /*+ "\n" */+ scanner.nextLine();
					}

					char[] charArray = theString.toCharArray();

					for (char c: charArray){
						System.out.print(c);
					}

					//Call parsing method to parse each CompilationUnit
					m.parse(charArray);

		  }



	}

	/**
		* List all the files under a directory
		* @param directoryName to be listed
		*/
	 public void listFiles(String directoryName){
			 File directory = new File(directoryName);
			 //get all the files from a directory
			 File[] fList = directory.listFiles();


			 for (File file : fList){
						if (file.isFile()){
							 System.out.println(file.getName());

						}
			 }
		}

		public File[] finder( String directoryName){
        File dir = new File(directoryName);

        return dir.listFiles(new FilenameFilter() {
                 public boolean accept(File dir, String filename)
                      { return filename.endsWith(".java"); }
        } );

    }

		public void parse(char[] sourceCode) {
			System.out.println("IN PARSE METHOD");
			ASTParser parser = ASTParser.newParser(AST.JLS8);

			parser.setKind(ASTParser.K_COMPILATION_UNIT);
			parser.setSource(sourceCode);
			parser.setResolveBindings(true);

			CompilationUnit cu = (CompilationUnit)parser.createAST(null);
			
		
			cu.accept(new ASTVisitor() {
				
				Set names = new HashSet();
				 
				public boolean visit(VariableDeclarationFragment node) {
					SimpleName name = node.getName();
					this.names.add(name.getIdentifier());
					System.out.println("Declaration of '"+name+"' at line"+cu.getLineNumber(name.getStartPosition()));
					return false; // do not continue to avoid usage info
				}
	 
				public boolean visit(SimpleName node) {
					if (this.names.contains(node.getIdentifier())) {
					System.out.println("Usage of '" + node + "' at line " +	cu.getLineNumber(node.getStartPosition()));
					}
					return false;
				}
			});
			
			System.out.println("finished visiting");
		/*
			TypeVisitor v = new TypeVisitor();
			System.out.println("created a new instance of TypeVisitor");
			cu.accept(v);
			System.out.println("finished visiting");
		*/	

		}


}
