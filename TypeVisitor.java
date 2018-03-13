import java.util.Iterator;
import org.eclipse.jdt.core.dom.*;

public class TypeVisitor extends ASTVisitor {
	
	int declarationCounter = 0;
	
	public boolean visit(VariableDeclarationStatement node) {
		
		System.out.println("In visit method");
		for (Iterator<?> iter = node.fragments().iterator(); iter.hasNext();) {
		
			VariableDeclarationFragment fragment = (VariableDeclarationFragment) iter.next();
			IVariableBinding binding = fragment.resolveBinding();
			declarationCounter++;
			System.out.println("binding variable declaration: "+binding.getVariableDeclaration());
			System.out.println("binding: " +binding);
			
			//String typeFound = binding.getVariableDeclaration().toString();
			//String typeSplit = typeFound.split(" "); //Simply get the type that was declared 
			//VariableBindingManager manager = new VariableBindingManager(fragment);
			//localVariableManagers.put(binding, manager);
		}
		System.out.println("# of Declarations: "+declarationCounter);
		return true;
	}
/*	
	//reference count 
	public boolean visitNode(SimpleName node){
		if (binding.equals(simpleName.resolveBinding())) {};
		
		
		IBinding binding = node.resolveBinding();
		if (localVariableManagers.containsKey(binding)) {
			VariableBindingManager manager = localVariableManagers.get(binding);
			manager.variableRefereneced(node);
		}
		return;
	}
*/
}


