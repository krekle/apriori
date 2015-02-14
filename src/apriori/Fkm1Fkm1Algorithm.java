package apriori;

import java.util.List;

/**
 * The class implementing the Apriori algorithm using the "F_k-1 x F_k-1" method.
 *
 * @param <V> item type
 * @author TODO: Your name.
 */
public class Fkm1Fkm1Algorithm<V> extends AbstractAprioriAlgorithm<V> {

    public Fkm1Fkm1Algorithm(List<ItemSet<V>> transactions) {
        super(transactions);
    }

    @Override
    public void generate(double minSupport, double minConfidence) {
        //TODO: CODE HERE PPL
        System.out.println("Needs to be implemented!");
    }

}
