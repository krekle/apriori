package apriori;

import java.util.*;

/**
 * The class implementing the Apriori algorithm using the "F_k-1 x F_k-1" method.
 *
 * @param <V> item type
 * @author TODO: Your name.
 */
public class Fkm1Fkm1Algorithm<V> extends AbstractAprioriAlgorithm<V> {

    // Initial Storage of unique elements
    private Set<ItemSet<V>> unique = new HashSet<ItemSet<V>>();
    // Unique list
    private HashMap<ItemSet<V>, Double> uniqueList = new HashMap<ItemSet<V>, Double>();

    private Set<V> temp = new HashSet<V>();
    //Boolean to know if we are finished with calculating support
    private boolean finished = false;


    public Fkm1Fkm1Algorithm(List<ItemSet<V>> transactions) {
        super(transactions);
    }

    @Override
    public void generate(double minSupport, double minConfidence) {
        super.minSupport = minSupport;
        super.minConfidence = minConfidence;

        while(finished == false){
            findUnique();
            calcSupport();
            for (ItemSet<V> key : uniqueList.keySet()) {
                System.out.println("Key: " + key + "  Support: " + uniqueList.get(key));
            }
        }
        calcConfidence();



    }

    private void findUnique() {
        // Just to find the initial unique items
        if (uniqueList.size() == 0) {
            if (temp.size() == 0) {
                for (ItemSet<V> trans_line : transactions) {
                    temp.addAll(trans_line.getItems());
                }
                findUnique();
                // Here vi have the unique items, now we make ItemSet of them
            } else {
                for (V item : temp) {
                    unique.add(new ItemSet<V>((V) item));
                }
            }
        } else {
            //Empty the uniques list
            unique = new HashSet<ItemSet<V>>();
            for(ItemSet<V> key : uniqueList.keySet()){
                for(ItemSet<V> k : uniqueList.keySet()){
                    if(!key.equals(k)){
                        unique.add(key.union(k));
                    }
                }
            }
            //Empty the current list
            uniqueList = new HashMap<ItemSet<V>, Double>();
        }

    }

    private int dimension = 1;

    // Method for calculating support
    private void calcSupport() {
        List<ItemSet<V>> dimention_list = new ArrayList<>();
        for (ItemSet<V> un : unique) {
            double total_hits = 0;
            for (ItemSet<V> item : super.transactions) {
                if (un.intersection(item).equals(un)) {
                    total_hits++;
                }

            }
            // Add to list if support is over min
            double support = total_hits / transactions.size();
            if (support >= super.minSupport) {
                uniqueList.put(un, support);
                dimention_list.add(un);
            }
        }

        frequentItemSets.put(dimension, dimention_list);
        dimension++;

        //Check if finished
        if(uniqueList.size() == 1){
            finished = true;
        }


    }

    // Method for calculating the confidence, requires support to be finished
    // UniqueList wil here contain all the 'winners'
    private void calcConfidence() {
        for(ItemSet<V> key: uniqueList.keySet()){
            System.out.println("CANDIDATES: \n" + key.toString());
            System.out.println(generateCandidates(key).toString());
        }
    }

    //TODO: generate Rules based on items
    private Set<AssociationRule<V>> generateCandidates(ItemSet<V> items){
        Set<AssociationRule<V>> res = new HashSet<>();
        //Helper for finding all permutations
        Permutations p = new Permutations();
        ArrayList<ArrayList<Object>> permutations;
        permutations = p.permute(items.getItems().toArray());


            int scope = items.size()-2;
            for(int i = 0; i < permutations.size(); i++) {
                ItemSet<V> antecent = new ItemSet<>();
                ItemSet<V> consequent = new ItemSet<>();
                for (int j = 0; j < permutations.get(i).size(); j++) {
                    if(j <= scope){
                        //System.out.println("Adding antecent: " + permutations.get(i).get(j));
                        antecent.addItem((V) permutations.get(i).get((j+scope)%items.size()));
                    }else {
                        //System.out.println("Adding consequent: " + permutations.get(i).get(j));
                        consequent.addItem((V) permutations.get(i).get((j+scope)%items.size()));
                    }
                }
                scope--;
                res.add(new AssociationRule<V>(antecent, consequent));
            }
//        }

        return res;
    }

}
