package de.frittenburger.meta.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.ArrayAccessExpr;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.CastExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.utils.StringEscapeUtils;

import de.frittenburger.meta.interfaces.SimpleJavaToMetaConverter;
import de.frittenburger.meta.model.Assign;
import de.frittenburger.meta.model.Code;
import de.frittenburger.meta.model.MetaAlgorithm;
import de.frittenburger.meta.model.MetaConst;
import de.frittenburger.meta.model.MetaFunction;
import de.frittenburger.meta.model.MetaFunctionCall;
import de.frittenburger.meta.model.MetaModel;
import de.frittenburger.meta.model.MetaModul;
import de.frittenburger.meta.model.MetaVariable;

public class SimpleJavaToMetaConverterImpl implements SimpleJavaToMetaConverter {

	@Override
	public MetaAlgorithm convert(File file) throws FileNotFoundException {
		
		
		CompilationUnit compilationUnit = StaticJavaParser.parse(file);
		MetaAlgorithm algorithm = new MetaAlgorithm();
	
		for(Node node : compilationUnit.getChildNodes())
		{
			if(node instanceof ClassOrInterfaceDeclaration)
			{
				ClassOrInterfaceDeclaration clazz = (ClassOrInterfaceDeclaration)node;
				algorithm.modul = new MetaModul();
				algorithm.modul.name = clazz.getNameAsString();
				algorithm.modul.models = convertFieldDeclarationList(clazz.getFields());
				algorithm.modul.functions = convertMethodDeclarationList(clazz.getMethods(),clazz.getNameAsString());
			}
			else if(node instanceof PackageDeclaration)
			{
				PackageDeclaration p = (PackageDeclaration)node;
				System.out.println("packagename "+p.getNameAsString());
			}
			else if(node instanceof ImportDeclaration)
			{
				ImportDeclaration i = (ImportDeclaration)node;
				System.out.println("import: "+i.getNameAsString());
			}
			else
			{
				System.out.println("Unknown type: "+node.getClass().getName());
			}
		}

		return algorithm;
	}

	private List<MetaFunction> convertMethodDeclarationList(List<MethodDeclaration> declarations,String clazz) {
		List<MetaFunction> functions = new ArrayList<>();
		for(MethodDeclaration declaration : declarations)
		{
			MetaFunction function = convertMethodDeclaration(declaration,clazz);
			functions.add(function);
		}
		return functions;
	}

	private MetaFunction convertMethodDeclaration(MethodDeclaration declaration,String clazz) {

		MetaFunction function = new MetaFunction();
		function.name = declaration.getNameAsString();
		function.staticProperty = declaration.isStatic();
		function.params = new ArrayList<>();
		if(!declaration.isStatic())
		{
			MetaVariable thisVar = new MetaVariable();
			thisVar.name = "this";
			thisVar.type = clazz;
			function.params.add(thisVar);
		}
		function.params.addAll(convertParameters(declaration.getParameters()));
		function.code = convertBlockStatement(declaration.getBody().get());
		return function;
	}
	
	private Code convertStatement(Statement statement) {
		
		if(statement.isExpressionStmt())
		{
			return convertExpression(statement.asExpressionStmt().getExpression());
		}
		if(statement.isForStmt())
		{
			return convertForStatement(statement.asForStmt());
		}
		if(statement.isIfStmt())
		{
			return convertIfStatement(statement.asIfStmt());
		}
		if(statement.isReturnStmt())
		{
			return convertReturnStatement(statement.asReturnStmt());
		}
		System.err.println("Unknown "+statement.getClass().getSimpleName());
		System.out.println(statement);
		return null;
	}
	
	private Code convertReturnStatement(ReturnStmt returnStmt) {
		Code code = new Code();
		
		Optional<Expression> expr = returnStmt.getExpression();
		if(expr.isPresent())
			code.returnStatement = convertExpression(expr.get());
		else
			code.returnStatement = createVoidConst();
		return code;
	}

	private Code createVoidConst() {
		Code code = new Code();
		code.constStatement = new MetaConst();
		code.constStatement.type = "void";
		code.constStatement.value = "void";
		return code;
	}

	private Code createClassConst(String name) {
		Code code = new Code();
		code.constStatement = new MetaConst();
		code.constStatement.type = "class";
		code.constStatement.value = name;
		return code;
	}
	
	private List<Code> convertBlockStatement(BlockStmt blockStmt) {

		List<Code> codes = new ArrayList<>();
		for(Statement statement : blockStmt.getStatements())
		{
			codes.add(convertStatement(statement));
		}
		
		return codes;
	}

	

	private List<Code> convertExpressionList(NodeList<Expression> expressions) {
		List<Code> codes = new ArrayList<>();
		for(Expression expr : expressions)
			codes.add(convertExpression(expr));
		return codes;
	}

	private Code convertForStatement(ForStmt forStmt) {

		
		Code code = new Code();
		
		code.forStatement = new ArrayList<>();
		
		NodeList<Expression> init = forStmt.getInitialization();
		if(init.size() != 1)
			throw new IllegalArgumentException("only exact one init statement allowed");
		code.forStatement.add(convertExpression(init.get(0)));
		
		Optional<Expression> compare = forStmt.getCompare();
		if(!compare.isPresent())
			throw new IllegalArgumentException("only exact one update compare allowed");
		code.forStatement.add(convertExpression(compare.get()));
		
		NodeList<Expression> update = forStmt.getUpdate();
		if(update.size() != 1)
			throw new IllegalArgumentException("only exact one update statement allowed");
		code.forStatement.add(convertExpression(update.get(0)));
		
		
		Statement doStatement = forStmt.getBody();
		if(doStatement.isBlockStmt())
		{
			code.doStatement = convertBlockStatement(doStatement.asBlockStmt());
		}
		else
		{
			code.doStatement = new ArrayList<>();
			code.doStatement.add(convertStatement(doStatement));
		}
		
		return code;
	}

	private Code convertIfStatement(IfStmt ifStmt) {
		
		Code code = new Code();
		
		code.ifStatement = convertExpression(ifStmt.getCondition());
		
		Statement thenStatement = ifStmt.getThenStmt();
		
		if(thenStatement.isBlockStmt())
		{
			code.thenStatement = convertBlockStatement(thenStatement.asBlockStmt());
		}
		else
		{
			code.thenStatement = new ArrayList<>();
			code.thenStatement.add(convertStatement(thenStatement));
		}
		return code;
	}

	
	
	private Code convertExpression(Expression expr) {
		
		
		if(expr.isVariableDeclarationExpr())
		{
			VariableDeclarationExpr declaration = expr.asVariableDeclarationExpr();
			Code code = new Code();
			code.variables = convertVariableDeclarationList(declaration.getVariables());
			return code;
		}
		
		if(expr.isAssignExpr())
		{
			AssignExpr a = expr.asAssignExpr();
			Code code = new Code();
			code.assignStatement = new Assign();
			code.assignStatement.var = a.getTarget().asNameExpr().getNameAsString();
			code.assignStatement.value = convertExpression(a.getValue());
			return code;
		}
		
		if(expr.isMethodCallExpr())
		{
			MethodCallExpr call = expr.asMethodCallExpr();

			Code code = new Code();
			code.fn = new MetaFunctionCall();
			switch(call.getNameAsString())
			{
				case "size":
					code.fn.name = "Size";
					break;
				case "get":
					code.fn.name = "Get";
					break;
				case "equals":
					code.fn.name = "isEquals";
					break;
				default:
					code.fn.name = call.getNameAsString();
					break;
			}
			
			code.fn.params = new ArrayList<>();
			
			Optional<Expression> scope = call.getScope();
			if(scope.isPresent())
			{
				code.fn.params.add(convertExpression(scope.get()));
			}
			code.fn.params.addAll(convertExpressionList(call.getArguments()));
			return code;

		}
		
		if(expr.isObjectCreationExpr())
		{
			
			ObjectCreationExpr call = expr.asObjectCreationExpr();

			Code code = new Code();
			code.fn = new MetaFunctionCall();
			code.fn.name = "New";
			code.fn.params = new ArrayList<>();
			
			ClassOrInterfaceType clazz = call.getType();
			
			code.fn.params.add(createClassConst(clazz.getNameAsString()));
			
			code.fn.params.addAll(convertExpressionList(call.getArguments()));
			return code;
		}
		
		if(expr.isFieldAccessExpr())
		{
			FieldAccessExpr call = expr.asFieldAccessExpr();

			Code code = new Code();
			code.fn = new MetaFunctionCall();
			code.fn.name = "Get";
			code.fn.params = new ArrayList<>();
			
			Expression scope = call.getScope();
			code.fn.params.add(convertExpression(scope));
			
			return code;
		}
		
		if(expr.isArrayAccessExpr())
		{
			ArrayAccessExpr call = expr.asArrayAccessExpr();

			Code code = new Code();
			code.fn = new MetaFunctionCall();
			code.fn.name = "Get";
			code.fn.params = new ArrayList<>();
			code.fn.params.add(convertExpression(call.getName()));
			code.fn.params.add(convertExpression(call.getIndex()));

			return code;
		}
		
		
		
		
		if(expr.isNameExpr())
		{
			NameExpr n = expr.asNameExpr();
			Code code = new Code();
			code.varStatement = n.getNameAsString();
			return code;
		}
		
		if(expr.isStringLiteralExpr())
		{
			StringLiteralExpr c = expr.asStringLiteralExpr();
			Code code = new Code();
			code.constStatement = new MetaConst();
			code.constStatement.type = "string";
			code.constStatement.value = StringEscapeUtils.unescapeJava(c.getValue());
			return code;
		}
		
		if(expr.isIntegerLiteralExpr())
		{
			IntegerLiteralExpr c = expr.asIntegerLiteralExpr();
			Code code = new Code();
			code.constStatement = new MetaConst();
			code.constStatement.type = "number";
			code.constStatement.value = c.getValue();
			return code;
		}
		
		if(expr.isBinaryExpr())
		{
			BinaryExpr b = expr.asBinaryExpr();
			
			Code code = new Code();
			code.fn = new MetaFunctionCall();
			switch(b.getOperator())
			{
				case LESS:
					code.fn.name = "isLessThen";
					break;
				case LESS_EQUALS:
					code.fn.name = "isLessEqualsThen";
					break;
				case PLUS:
					code.fn.name = "Add";
					break;
				case MINUS:
					code.fn.name = "Sub";
					break;
				case MULTIPLY:
					code.fn.name = "Mul";
					break;
				case AND:
					code.fn.name = "And";
					break;
				case EQUALS:
					code.fn.name = "isEquals";
					break;
				default:
					code.fn.name = b.getOperator().toString();
					break;
			}
			
			code.fn.params = new ArrayList<>();
			
			code.fn.params.add(convertExpression(b.getLeft()));
			code.fn.params.add(convertExpression(b.getRight()));
			return code;
			
		}
		
		/*
		if(expr.isUnaryExpr())
		{
			UnaryExpr u = expr.asUnaryExpr();
			
			Code code = new Code();
			code.fn = new MetaFunctionCall();
			code.fn.name = u.getOperator().toString();
			code.fn.params = new ArrayList<>();
			
			code.fn.params.add(convertExpression(u.getExpression()));
			return code;
			
		}
		*/
		if(expr.isCastExpr())
		{
			CastExpr n = expr.asCastExpr();
			String castType = n.getType().asString();
			if(castType.equals("int"))
				return convertExpression(n.getExpression());
			throw new IllegalArgumentException("Unknown cast "+castType);
		}
		
		throw new IllegalArgumentException("Unknown "+expr.getClass().getSimpleName()+" >>> "+expr);
		
	}



	

	private List<MetaVariable> convertVariableDeclarationList(NodeList<VariableDeclarator> declarations) {
		List<MetaVariable> variables = new ArrayList<>();
		for(VariableDeclarator declaration : declarations)
		{
			if(declaration.getInitializer().isPresent())
				throw new IllegalArgumentException("initializer not allowed "+declaration);
			
			MetaVariable var = createVariable(declaration.getNameAsString(),declaration.getTypeAsString());
			variables.add(var);
		}
			
		return variables;
	}

	private List<MetaVariable> convertParameters(NodeList<Parameter> parameters) {

		List<MetaVariable> variables = new ArrayList<>();
		
		
		Iterator<Parameter> it = parameters.iterator();
		while(it.hasNext())
		{
			Parameter par = it.next();
			MetaVariable var = createVariable(par.getNameAsString(),par.getTypeAsString());
			
		
						
			//Todo Convert String[] to array + items: type string
			variables.add(var);
		}
		
		return variables;
	}

	private MetaVariable createVariable(String name, String type) {
		
		MetaVariable var = new MetaVariable();
		var.name = name;
		
		switch(type)
		{
		case "String":
			var.type = "string";
			break;
		case "int":
			var.type = "number";
			break;
		case "int[]":
	
			var.type = "list";
			var.items = createVariable(null,"int");
			break;
		case "String[]":
		case "List<String>":
			var.type = "list";
			var.items = createVariable(null,"String");
			
			break;
		default:
			var.type = "object";
			break;	
		}
			
		return var;
	}

	private List<MetaModel> convertFieldDeclarationList(List<FieldDeclaration> declarations) {
		List<MetaModel> models = new ArrayList<>();
		for(FieldDeclaration declaration : declarations)
		{
			System.out.println("Not implemented: "+declaration);
		}
		return models;
	}

	public static void main(String args[]) throws IOException
	{
		SimpleJavaToMetaConverter converter = new SimpleJavaToMetaConverterImpl();
		
		MetaAlgorithm algorithm = converter.convert(new File(args[0]));
		
		new ObjectMapper(new YAMLFactory()).writerWithDefaultPrettyPrinter().writeValue(new File(args[1]),algorithm);
	}
}
