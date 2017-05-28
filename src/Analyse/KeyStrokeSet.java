package Analyse;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import Database.Request;
import KeystrokeMeasuring.KeyStroke;
import Main.Account;

	public class KeyStrokeSet {
		
		private LinkedList<KeyStroke> set;
		
		public KeyStrokeSet(LinkedList<KeyStroke> set){
			this.setSet(new LinkedList<KeyStroke>(set));
			Iterator<KeyStroke> itr = set.iterator();
			KeyStroke cur=set.getFirst();
			KeyStroke next;
			while (itr.hasNext()){
				next = itr.next();
				cur.setNext(next);
				cur=next;
			}
		}
	
	public static LinkedList<KeyStrokeSet> buildReferenceSet(Account account){
		Connection conn = Main.Main.conn;
		LinkedList <KeyStrokeSet> sets = new LinkedList<KeyStrokeSet>();
		int[] refIndexes = Request.getLastSuccessfulEntries(account,conn);
		System.out.println("indexes : " + refIndexes.length);
		
			for (int k =0; k<refIndexes.length;k++){
				LinkedList <KeyStroke> keys = new LinkedList <KeyStroke>();
				ArrayList <ArrayList>keysForEntry = Request.getTouchesForEntry(refIndexes[k],conn);
				//System.out.println("keys :" + keysForEntry.size());
				for(int j=0;j<keysForEntry.size();j++){
					ArrayList<String>encryptedValues = new ArrayList<String>(15);
					
					keys.add(new KeyStroke(new ArrayList(keysForEntry.get(j)),account));
				}
				if(keys.size()>0)
				sets.add(new KeyStrokeSet (keys));
				System.out.print("#");
			}
			//System.out.println("sets" + sets.size());
			System.out.println("\n");
			return sets;
	}

	public LinkedList<KeyStroke> getSet() {
		return set;
	}

	public void setSet(LinkedList<KeyStroke> set) {
		this.set =	this.set=new LinkedList<KeyStroke>(set);;
	}

}