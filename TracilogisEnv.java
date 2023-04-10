// ----------------------------------------------------------------------------
// Copyright (C) 2014 Louise A. Dennis and Michael Fisher 
// 
// This file is part of Gwendolen
//
// Gwendolen is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
// 
// Gwendolen is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public
// License along with Gwendolen if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
// 
// To contact the authors:
// http://www.csc.liv.ac.uk/~lad
//----------------------------------------------------------------------------

package version11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import ail.mas.DefaultEnvironment;
import ail.syntax.Action;
import ail.syntax.Unifier;
import ail.syntax.NumberTerm;
import ail.syntax.NumberTermImpl;
import ail.syntax.Predicate;
import ail.util.AILexception;
import ajpf.util.AJPFLogger;
import ajpf.util.VerifySet;

/**
 * This is a simple class representing a search and rescue environment on a grid.  For use 
 * with the Gwendolen Tutorials.
 * @author lad
 *
 */
public class TracilogisEnv extends DefaultEnvironment {
	String logname = "env.TracilogisEnv";
	
	int prod_x = 0;
	int prod_y = 3;
	Random random = new Random();
	Predicate drill = new Predicate("Drill");
	Predicate groove = new Predicate("Groove");
	Predicate no = new Predicate("No");
	
	Predicate drilled = new Predicate("drilled");
	Predicate grooved = new Predicate("grooved");
	
	Predicate errorMq1 = new Predicate("ErrorMq1");
	Predicate successMq1 = new Predicate("SuccessMq1");
	Predicate errorMq2 = new Predicate("ErrorMq2");
	Predicate successMq2 = new Predicate("SuccessMq2");
	
	Predicate errorM1 = new Predicate("ErrorM1");
	Predicate successM1 = new Predicate("SuccessM1");
	Predicate errorM2 = new Predicate("ErrorM2");
	Predicate successM2 = new Predicate("SuccessM2");
	
	Predicate traceProd = new Predicate("Trace");
	Predicate traceProd1 = new Predicate("Trace");
	
	Predicate posChecked = new Predicate("positionChecked");
	boolean warning_message = false;
	boolean nowarning_message = false;
	
	Predicate posT = new Predicate("pos");

	
    HashMap<String, String> agentNames = new HashMap<String, String>();
    protected VerifySet<Predicate> positionPercepts = new VerifySet<Predicate>();
	
	String prod ="";
	String as ="";
	
	// ----------------------------------------------------------------------------
	// This part involves the introduction of ethical choices into the multi-agent system.
	//----------------------------------------------------------------------------
	String rule1 = "avoidWorkersInjured";
	String rule2 = "avoidWorkersDied";
	String rule3 = "avoidHugeDamage";
	String rule4 = "avoidMediumDamage";
	String rule5 = "avoidLossOfEscapingTime";
	
	Predicate ethicalRule1 = new Predicate("e");
	Predicate ethicalRule2 = new Predicate("e");
	Predicate ethicalRule3 = new Predicate("e");
	Predicate ethicalRule4 = new Predicate("e");
	Predicate ethicalRule5 = new Predicate("e");
	
	Predicate avoidWorkersInjured = new Predicate(rule1);
	Predicate avoidWorkersDied = new Predicate(rule2);
	Predicate avoidHugeDamage = new Predicate(rule3);
	Predicate avoidMediumDamage = new Predicate(rule4);
	Predicate avoidLossOfEscapingTime = new Predicate(rule5);
	
	VerifySet<Predicate> ethicalRules = new VerifySet<Predicate>();
	
	ArrayList<String> fireEmergP1VioE = new ArrayList<String> ();
	ArrayList<String> fireEmergP2VioE = new ArrayList<String> ();
	ArrayList<String> fireEmergP3VioE = new ArrayList<String> ();
	ArrayList<String> fireEmergP4VioE = new ArrayList<String> ();
	ArrayList<String> fireEmergP5VioE = new ArrayList<String> ();
	ArrayList<String> fireEmergP6VioE = new ArrayList<String> ();
	
	ArrayList<Double> fireEmegPlansSeverity = new ArrayList<Double>();
	//fireEmegPlansSeverity[0] = 5; It means the severity of plan 1 is 5.
	
	@Override
	public void init_after_adding_agents() {
		
	//	System.out.println("init_after_adding_agents");
		Predicate old_pos = new Predicate("pos");	
	//	System.out.println("flunch: "+old_pos.getFunctor());
		Predicate unblocked = new Predicate("Unblocked");
		old_pos.addTerm(new NumberTermImpl(prod_x));
		old_pos.addTerm(new NumberTermImpl(prod_y));
		traceProd.addTerm(old_pos);
		traceProd1.addTerm(old_pos);
		
		addPercept("prod",old_pos);
		addPercept("prod",traceProd);
		addPercept("prod1",old_pos);
		addPercept("prod1",traceProd1);
	//	addPercept("as0",unblocked);
		addPercept("AS1",unblocked);
		addPercept("AS2",unblocked);
		addPercept("Mq1",unblocked);
		addPercept("Mq2",unblocked);
		addPercept("M1",unblocked);
		addPercept("M2",unblocked);
		addPercept("checkP",unblocked);
		
		posT.addTerm(new NumberTermImpl(1));
		posT.addTerm(new NumberTermImpl(3));

		agentNames.put("as0", "AS0");
		agentNames.put("as1", "AS1");
		agentNames.put("as2", "AS2");
		agentNames.put("as3", "AS3");
		
		//initialize ethical rules
		//E(avoidWorkersInjured,3,0.5)
		ethicalRule1.addTerm(avoidWorkersInjured);
		ethicalRule1.addTerm(new NumberTermImpl(3));//severity
		ethicalRule1.addTerm(new NumberTermImpl(1));//the probability that the agent believes this ethical rule
	
		ethicalRule2.addTerm(avoidWorkersDied);
		ethicalRule2.addTerm(new NumberTermImpl(10));
		ethicalRule2.addTerm(new NumberTermImpl(1));
		
		ethicalRule3.addTerm(avoidHugeDamage);
		ethicalRule3.addTerm(new NumberTermImpl(10));
		ethicalRule3.addTerm(new NumberTermImpl(0.4));
		
		ethicalRule4.addTerm(avoidMediumDamage);
		ethicalRule4.addTerm(new NumberTermImpl(2));
		ethicalRule4.addTerm(new NumberTermImpl(1));

		ethicalRule5.addTerm(avoidLossOfEscapingTime);
		ethicalRule5.addTerm(new NumberTermImpl(3));
		ethicalRule5.addTerm(new NumberTermImpl(1));
		
		//add ethical rules to agents
		addPercept("iss",ethicalRule1);
		addPercept("iss",ethicalRule2);
		addPercept("iss",ethicalRule3);
		addPercept("iss",ethicalRule4);
		addPercept("iss",ethicalRule5);
		
//		consultPercept("iss");
		
		//add violated ethical rules to plans
		fireEmergP1VioE.add(rule1);
		fireEmergP1VioE.add(rule4);
		fireEmergP1VioE.add(rule5);
		
		fireEmergP2VioE.add(rule2);
		fireEmergP2VioE.add(rule4);
		fireEmergP2VioE.add(rule5);
		
		fireEmergP3VioE.add(rule1);
		fireEmergP3VioE.add(rule2);
		fireEmergP3VioE.add(rule4);
		fireEmergP3VioE.add(rule5);
		
		fireEmergP4VioE.add(rule1);
		fireEmergP4VioE.add(rule3);
		
		fireEmergP5VioE.add(rule2);
		fireEmergP5VioE.add(rule3);
		
		fireEmergP6VioE.add(rule1);
		fireEmergP6VioE.add(rule2);
		fireEmergP6VioE.add(rule3); 
		
		fireEmegPlansSeverity.add(0,0.0);
		fireEmegPlansSeverity.add(1,0.0);
		fireEmegPlansSeverity.add(2,0.0);
		fireEmegPlansSeverity.add(3,0.0);
		fireEmegPlansSeverity.add(4,0.0);
		fireEmegPlansSeverity.add(5,0.0);
		 
	}
	
	public Unifier executeAction(String agName, Action act) throws AILexception {
		Unifier u = new Unifier();


		while(containsPercept("prod",posT) && containsPercept("prod1",posT)) {
			
			System.err.println("ERROR");
			
		}
		
		
		if(act.getFunctor().equals("move")) {
			prod = act.getTerm(0).toString();
			
//			if(prod.equals("prod1")) {
//			
//				System.out.println("Before remove:++++++++++++++++");
//				System.out.println("consult prod");
//				consultPercept(prod);
//				System.out.println("Before remove:++++++++++++++++");
//			}
			Predicate oldPos = getPos(prod);
			removePos(prod);
			positionPercepts.remove(oldPos);
			
//			if(prod.equals("prod1")) {
//				System.out.println("");
//	
//				System.out.println("");
//				System.out.println("After remove:-----------------");
//				System.out.println("consult prod(1)");
//				consultPercept(prod);
//				System.out.println("After remove:-----------------");
//			}
			
			Predicate pos = new Predicate("pos");
			int x = (int) ((NumberTerm) act.getTerm(1)).solve();
			int y = (int) ((NumberTerm) act.getTerm(2)).solve();
			pos.addTerm(new NumberTermImpl(x));
			pos.addTerm(new NumberTermImpl(y));
			addPercept(prod,pos);
			
		//	addPercept(pos); // this makes every agent perceives the position, which is not correct.
			positionPercepts.add(pos);
//			System.out.println("@@@@@@@-----------------");
//			System.out.println("consult percepts of position");
//			for (Predicate l: positionPercepts) {
//				System.out.println(l.toString());	
//			}
//			System.out.println("@@@@@@@-----------------");
			
			Predicate trace = getPredicateTrace(prod);
			trace.addTerm(pos);
			addPercept(prod,trace);
			
//			if(prod.equals("prod1")) {
//				System.out.println("current : "+pos+","+prod);
//				System.out.println("trace : "+prod+","+trace);
//				
//				System.out.println("");
//				System.out.println("After add:-----------------");
//				System.out.println("consult prod(1)");
//				consultPercept(prod);
//				System.out.println("After add:-----------------");
//			}
			//planRoute(front)	
			
		} else if(act.getFunctor().equals("planRoute")) {
		
			String route = act.getTerm(0).toString();
			
			if(route.equals("front")){
				
			} else if(route.equals("middle")) {
				
			} else if(route.equals("back")) {
				
			} 
			Predicate evacuated = new Predicate("evacuated");
			addPercept(agName,evacuated);

		
		} else if(act.getFunctor().equals("check")) {
		
			prod = act.getTerm(0).toString();
			double t = ((NumberTerm) act.getTerm(1)).solve();
			clearPercepts(agName);
			if(t == 1){
				addPercept(prod,drill);
				addPercept(agName,drill);

			} else if (t == 2){
				addPercept(prod,groove);
				addPercept(agName,groove);

			} else {
			//	addPercept(prod,no);
				addPercept(agName,no);

			}
			Predicate checked = new Predicate("checked");
			addPercept(agName,checked);
			
		} else if(act.getFunctor().equals("checkPos")) {
			//For instance, checkPos(as0,prod,1,3)
			as = act.getTerm(0).toString();
			
			Predicate pos = new Predicate("pos");
			int x = (int) ((NumberTerm) act.getTerm(2)).solve();
			int y = (int) ((NumberTerm) act.getTerm(3)).solve();
			pos.addTerm(new NumberTermImpl(x));
			pos.addTerm(new NumberTermImpl(y));
			
			if(!positionPercepts.contains(pos)) {
				
				//addPercept(agentNames.get(as),posChecked);
			//	addPercept(as,posChecked);  //positionChecked
				addPercept(agName,posChecked);//positionChecked
			//	System.out.println("Next position checked for " +act.getTerm(1).toString());
				
			} else {
				Predicate failure = new Predicate("failure");
				addPercept(agName,failure);
			//	System.out.println("Next position is not available for "+act.getTerm(1).toString());
			}
	//		System.out.println("consult percepts for " +as);
			consultPercept(as);
	//		System.out.println("consult percepts for " +agName);
			consultPercept(agName);

			
		} else if(act.getFunctor().equals("checkEthicalChoices")) {
			//checkEthicalChoices(fireEmergency)
			String plan = act.getTerm(0).toString();
			//check the real-time ethical rules in the agent
			ethicalRules = getEthicalPercepts(agName);
			double lastSeverity = 0.0;
			double newSeverity = 0.0;
			double probability = 0.0;
			double min = 100;
			switch(plan) {
				case "fireEmergency":
					//(plan,severity)
					//fireEmergP1VioE = [rule1, rule4, rule5]
					//E(avoidWorkersInjured,3,0.5)
					for (Predicate l: ethicalRules) {
						// if fireEmergP1 violates rule1, rule2,..., rule n
						newSeverity = ((NumberTerm) l.getTerm(1)).solve();
						probability = ((NumberTerm) l.getTerm(2)).solve();
						if (fireEmergP1VioE.contains(l.getTerm(0).toString())) {
							lastSeverity = fireEmegPlansSeverity.get(0);
							fireEmegPlansSeverity.set(0, lastSeverity + newSeverity*probability);
						} 
						
						if (fireEmergP2VioE.contains(l.getTerm(0).toString())) {
							lastSeverity = fireEmegPlansSeverity.get(1);
							fireEmegPlansSeverity.set(1,lastSeverity + newSeverity*probability);
						} 
						
						if (fireEmergP3VioE.contains(l.getTerm(0).toString())) {
							lastSeverity = fireEmegPlansSeverity.get(2);
							fireEmegPlansSeverity.set(2,lastSeverity + newSeverity*probability);
						} 
						
						if (fireEmergP4VioE.contains(l.getTerm(0).toString())) {
							lastSeverity = fireEmegPlansSeverity.get(3);
							fireEmegPlansSeverity.set(3,lastSeverity + newSeverity*probability);
						} 
						
						if (fireEmergP5VioE.contains(l.getTerm(0).toString())) {
							lastSeverity = fireEmegPlansSeverity.get(4);
							fireEmegPlansSeverity.set(4,lastSeverity + newSeverity*probability);
						} 
						
						if (fireEmergP6VioE.contains(l.getTerm(0).toString())) {
							lastSeverity = fireEmegPlansSeverity.get(5);
							fireEmegPlansSeverity.set(5,lastSeverity + newSeverity*probability);
						} 
					}
					
				
					//fireEmegPlansSeverity = {5,7,9,6,10}
					for (double s: fireEmegPlansSeverity) {
						if(s < min) {
							min = s;
						}
					}
					
					// plan = "fireEmergency"
					Predicate choicePlan = new Predicate(plan);
					// the number of plans starts from 1 instead of 0
					choicePlan.addTerm(new NumberTermImpl(fireEmegPlansSeverity.indexOf(min)+1));
					// here, we will add belief severity (fireEmergency, 4), so the program will choose plan 4
					addPercept(agName,choicePlan);
//					System.out.println("iss");
//					consultPercept("iss");
					break;
				default:
//					System.out.println(plan+" does not exist! Please check!");
			
			}
			
			
			
			
		}else if(act.getFunctor().equals("drill")) {
			
			prod = act.getTerm(0).toString();
			addPercept(prod, drilled);
			removePercept(prod,drill);
			removePercept(agName,drill);
			
		} else if(act.getFunctor().equals("groove")) {
			/**/
			prod = act.getTerm(0).toString();
			addPercept(prod, grooved);
			removePercept(prod,groove);
			removePercept(agName,groove);
			
		} else if(act.getFunctor().equals("checkMq1")) {
			
			prod = act.getTerm(0).toString();
			if(containsPercept(prod,errorMq1)) {
				removePercept(prod,errorMq1);
				addPercept(prod,successMq1);
	//			System.out.println("After error:");
	
			} else {
					//addPercept("prod",errorMq1); as a test
					
					if(random.nextInt(10)==1) {
						addPercept(prod,errorMq1);
					} else {
						addPercept(prod,successMq1);
					}
			}
			
		} else if(act.getFunctor().equals("checkMq2")) {
			
			prod = act.getTerm(0).toString();
			if(containsPercept(prod,errorMq2)) {
				removePercept(prod,errorMq2);
				addPercept(prod,successMq2);
			} else {
					//addPercept("prod",errorMq2); as a test
					
					if(random.nextInt(10)==1) {
						addPercept(prod,errorMq2);
					} else {
						addPercept(prod,successMq2);
					}
			}
			
		} else if(act.getFunctor().equals("putLeft")) {
			/**/
			Predicate putLeft = new Predicate("putLeft");
			prod = act.getTerm(0).toString();
			putLeft.addTerm(act.getTerm(1));
			putLeft.addTerm(act.getTerm(2));
			addPercept(prod,putLeft);

		} else if(act.getFunctor().equals("putRight")) {
			/**/
			Predicate putRight = new Predicate("putRight");
			prod = act.getTerm(0).toString();
			putRight.addTerm(act.getTerm(1));
			putRight.addTerm(act.getTerm(2));
			addPercept(prod,putRight);

		} else if(act.getFunctor().equals("checkM1")) {
			/**/
			prod = act.getTerm(0).toString();
			if(containsPercept(prod,errorM1)) {
				removePercept(prod,errorM1);
				addPercept(prod,successM1);
			} else {
					addPercept(prod,errorM1); 
				/*	
					if(random.nextInt(10)==1) {
						addPercept("prod",errorM1);
					} else {
						addPercept("prod",successM1);
					}*/
			}

		} else if(act.getFunctor().equals("checkM2")) {
			/**/
			prod = act.getTerm(0).toString();
			if(containsPercept(prod,errorM2)) {
				removePercept(prod,errorM2);
				addPercept(prod,successM2);
			} else {
					//addPercept("prod",errorMq2); as a test
					
					if(random.nextInt(10)==1) {
						addPercept(prod,errorM2);
					} else {
						addPercept(prod,successM2);
					}
			}

		} else if(act.getFunctor().equals("add")) {
			/**/
			if(act.getTerm(0).toString()!=null) {
				
				Predicate belief = new Predicate(act.getTerm(0).toString());
				addPercept(agName,belief);
			}
			else {
	//			System.err.println("There is nothing to add!!");
			}
			
			

		} else if(act.getFunctor().equals("delete")) {
			/**/
			Predicate belief = new Predicate(act.getTerm(0).toString());
			int a = act.getTermsSize();
			if(containsPercept(agName,belief)) {
				removePercept(agName,belief);
//				System.out.println("consult percepts for " +agName+" after delete "+act.getTerm(0).toString());
			//	consultPercept(agName);
			}
			else {
				
//				System.out.println("There is no such belief!!");
			}

		} else if(act.getFunctor().equals("deletePosCheckedFromBS")) {
			/**/
			removePercept(posChecked);
			clearPercepts(agName);
		} else if(act.getFunctor().equals("clear")) {
			/**/
	
			clearPercepts(agName);
		} 
		super.executeAction(agName, act);
		
	
		
		return u;
	}

}
