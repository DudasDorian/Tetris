package Logic;

import Logic.Blocks.*;
// Part of the Factory Design Pattern. It's responsible with creating each concrete instance of a Block.
public class BlockFactory {
    // Returns a piece correlating to a number.
    public Block getBlock(int i){
        switch (i) {
            case 0 -> {
                return new I_Block();
            }
            case 1 -> {
                return new O_Block();
            }
            case 2 -> {
                return new T_Block();
            }
            case 3 -> {
                return new S_Block();
            }
            case 4 -> {
                return new Z_Block();
            }
            case 5 -> {
                return new J_Block();
            }
            case 6 -> {
                return new L_Block();
            }
        }
        return new I_Block();
    }
}
