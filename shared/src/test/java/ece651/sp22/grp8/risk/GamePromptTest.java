package ece651.sp22.grp8.risk;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GamePromptTest {
    @Test
    public void test_all(){
        GamePrompt g = new GamePrompt();
        assertEquals("ok!\n",g.OK);
    }

}