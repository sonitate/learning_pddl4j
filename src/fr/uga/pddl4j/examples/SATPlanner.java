package fr.uga.pddl4j.examples;
//package fr.uga.pddl4j.examples.SATEncoding;
import fr.uga.pddl4j.planners.AbstractPlanner;
import fr.uga.pddl4j.planners.AbstractPlanner;
import fr.uga.pddl4j.parser.DefaultParsedProblem;
import fr.uga.pddl4j.parser.RequireKey;
import fr.uga.pddl4j.parser.Symbol;

import fr.uga.pddl4j.problem.Problem;
//import fr.uga.pddl4j.problem.Action;
import fr.uga.pddl4j.problem.InitialState;
//import fr.uga.pddl4j.problem.Condition;
import fr.uga.pddl4j.parser.Expression;

import fr.uga.pddl4j.parser.ParsedProblem;
import fr.uga.pddl4j.parser.ParsedDomain;
import fr.uga.pddl4j.plan.Plan;
import fr.uga.pddl4j.plan.SequentialPlan;
import fr.uga.pddl4j.planners.statespace.AbstractStateSpacePlanner;
import fr.uga.pddl4j.planners.Planner;
import fr.uga.pddl4j.planners.PlannerConfiguration;
import fr.uga.pddl4j.planners.ProblemNotSupportedException;
import fr.uga.pddl4j.planners.SearchStrategy;
import fr.uga.pddl4j.planners.statespace.search.StateSpaceSearch;
import fr.uga.pddl4j.problem.DefaultProblem;
import fr.uga.pddl4j.problem.Problem;
import fr.uga.pddl4j.problem.State;
import fr.uga.pddl4j.problem.operator.Action;
import fr.uga.pddl4j.problem.operator.ConditionalEffect;
import fr.uga.pddl4j.problem.operator.Condition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.Comparator;
import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
//import java.util.Set;
import java.util.Map;
import java.util.Set;
import java.io.FileNotFoundException;
import fr.uga.pddl4j.parser.DefaultParsedProblem;
import fr.uga.pddl4j.parser.ErrorManager;
import fr.uga.pddl4j.parser.Message;
import fr.uga.pddl4j.parser.Parser;

import org.sat4j.reader.DimacsReader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

public class SATPlanner {

    /**
     * The main method the class. The first argument must be the path to the PDDL domain description and the second
     * argument the path to the PDDL problem description.
     *
     * @param args the command line arguments.
     */
    public static void main(final String[] args) {



        // Checks the number of arguments from the command line
        if (args.length != 2) {
            System.out.println("Invalid command line");
            return;
        }

        try {
            // Creates an instance of the PDDL parser
            final Parser parser = new Parser();
            // Parses the domain and the problem files.
            final DefaultParsedProblem parsedProblem = parser.parse(args[0], args[1]);
            // Gets the error manager of the parser
            final ErrorManager errorManager = parser.getErrorManager();
            // Checks if the error manager contains errors
            if (!errorManager.isEmpty()) {
                // Prints the errors
                for (Message m : errorManager.getMessages()) {
                    System.out.println(m.toString());
                }
            } else {
                // Prints that the domain and the problem were successfully parsed
                System.out.print("\nparsing domain file \"" + args[0] + "\" done successfully");
                System.out.print("\nparsing problem file \"" + args[1] + "\" done successfully\n\n");
                // Create a problem
                final Problem problem = new DefaultProblem(parsedProblem);
                // Instantiate the planning problem
                problem.instantiate();
                final int MAXVAR = 1000000;
                final int NBCLAUSES = 500000;

                ISolver solver = SolverFactory.newDefault();

// prepare the solver to accept MAXVAR variables. MANDATORY for MAXSAT solving
                solver.newVar(MAXVAR);
                solver.setExpectedNumberOfClauses(NBCLAUSES);
// Feed the solver using Dimacs format, using arrays of int
// (best option to avoid dependencies on SAT4J IVecInt)
                //encoding!!!
                for (int i=0;<NBCLAUSES;i++) {
                    int [] clause = {};
                    try {
                        solver.addClause(new VecInt(clause)); // adapt Array to IVecInt
                    } catch (ContradictionException e){
                        System.out.println("SAT encoding failure!");
                        System.exit(0);
                    }
                }


                // we are done. Working now on the IProblem interface
                IProblem problem = solver;
                try {
                    if (ip.isSatisfiable()) {
                    } else {
                    }
                } catch (TimeoutException e){
                    System.out.println("Timeout! No solution found!");
                    System.exit(0);
                }

                // Print the list of actions of the instantiated problem
//                for (Action a : problem.getActions()) {
//                    System.out.println(problem.toString(a));
//                }
            }
            // This exception could happen if the domain or the problem does not exist
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    }






