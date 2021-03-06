package org.uscxml.tests;

import org.uscxml.Data;
import org.uscxml.DataNative;
import org.uscxml.Event;
import org.uscxml.Factory;
import org.uscxml.Interpreter;
import org.uscxml.JavaDataModel;
import org.uscxml.StringSet;


public class TestDataModel extends JavaDataModel {
	
	@Override
	public JavaDataModel create(Interpreter interpreter) {
		/**
		 * Called when an SCXML interpreter wants an instance of this datamodel
		 */
		System.out.println("create");
		return new TestDataModel();
	}

	@Override
	public StringSet getNames() {
		/**
		 * Register with the following names for the datamodel attribute
		 * at the scxml element. <scxml datamodel="one of these">
		 */
		System.out.println("getNames");
		StringSet ss = new StringSet();
		ss.insert("java");
		return ss;
	}

	@Override
	public boolean validate(String location, String schema) {
		/**
		 * Validate the datamodel. This make more sense for XML datamodels
		 * and is pretty much unused but required as per draft.
		 */
		System.out.println("validate " + location + " " + schema);
		return true;
	}

	@Override
	public void setEvent(Event event) {
		/**
		 * Make the current event available as the variable _event
		 * in the datamodel.
		 */
		System.out.println("setEvent " + event);
	}

	@Override
	public DataNative getStringAsData(String content) {
		/**
		 * Evaluate the string as a value expression and
		 * transform it into a JSON-like Data structure
		 */
		System.out.println("getStringAsData " + content);
		Data data = new Data();
		return Data.toNative(data);
	}

	@Override
	public long getLength(String expr) {
		/**
		 * Return the length of the expression if it were an
		 * array, used by foreach element.
		 */
		System.out.println("getLength " + expr);
		return 0;
	}

	@Override
	public void setForeach(String item, String array, String index, long iteration) {
		/**
		 * Prepare an iteration of the foreach element, by setting the variable in index
		 * to the current iteration and setting the variable in item to the current item
		 * from array.
		 */
		System.out.println("setForeach " + item + " " + index + " " + iteration);
	}

	@Override
	public void eval(String scriptElem, String expr) {
		/**
		 * Evaluate the given expression in the datamodel.
		 * This is used foremost with script elements.
		 */
		System.out.println("eval " + scriptElem + " " + expr);
	}

	@Override
	public String evalAsString(String expr) {
		/**
		 * Evaluate the expression as a string e.g. for the log element. 
		 */
		System.out.println("evalAsString " + expr);
		return "";
	}

	@Override
	public boolean evalAsBool(String expr) {
		/**
		 * Evaluate the expression as a boolean for cond attributes in
		 * if and transition elements.
		 */
		System.out.println("evalAsBool " + expr);
		return true;
	}

	@Override
	public boolean isDeclared(String expr) {
		/**
		 * The interpreter is supposed to raise an error if we assign
		 * to an undeclared variable. This method is used to check whether
		 * a location from assign is declared.
		 */
		System.out.println("isDeclared " + expr);
		return true;
	}

	@Override
	public void init(String dataElem, String location, String content) {
		/**
		 * Called when we pass data elements.
		 */
		System.out.println("init " + dataElem + " " + location + " " + content);
	}

	@Override
	public void assign(String assignElem, String location, String content) {
		/**
		 * Called when we evaluate assign elements
		 */
		System.out.println("assign " + assignElem + " " + location + " " + content);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// load JNI library from build directory
		System.load("/Users/sradomski/Documents/TK/Code/uscxml/build/cli/lib/libuscxmlNativeJava64_d.jnilib");
		
		// register java datamodel at factory
		TestDataModel datamodel = new TestDataModel(); 
		Factory.getInstance().registerDataModel(datamodel);
		
		// instantiate interpreter with document from file
		Interpreter interpreter = Interpreter.fromURI("/Users/sradomski/Documents/TK/Code/uscxml/test/samples/uscxml/test-java-datamodel.scxml");

		// wait until interpreter has finished
		while(true)
			interpreter.interpret();
	}

}
