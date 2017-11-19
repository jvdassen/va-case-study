package ch.uzh.ifi.seal.soprafs16.model;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import junit.framework.Assert;
/*
 * @author Jan von der Assen
 */
public class CardstackTest {
	
	CardStack testStack = new CardStack();
	long gameid = 9;
	long userid = 5;
	
	ActionCard testActionCardClimb = new ActionCard();
	ActionCard testActionCardMarshal = new ActionCard();
	ActionCard testActionCardMove = new ActionCard();	
	ActionCard testActionCardPunch = new ActionCard();	
	ActionCard testActionCardRob = new ActionCard();	
	ActionCard testActionCardShoot = new ActionCard();	
	
	ActionCard notInStack = new ActionCard();
	
	RoundCard testRoundCardAM = new RoundCard();
	RoundCard testRoundCardBrake = new RoundCard();
	RoundCard testRoundCardCrane = new RoundCard();
	RoundCard testRoundCardHostage = new RoundCard();
	RoundCard testRoundCardNothing = new RoundCard();	
	
	SpecialEvent testSpecialEvent = new SpecialEvent();

	AmmoCard testAmmoCard6 = new AmmoCard();
	AmmoCard testAmmoCard5 = new AmmoCard();
	AmmoCard testAmmoCard4 = new AmmoCard();
	AmmoCard testAmmoCard3 = new AmmoCard();
	AmmoCard testAmmoCard2 = new AmmoCard();
	AmmoCard testAmmoCard1 = new AmmoCard();	
	
	Move testMove1 = new Move();
	Move testMove2 = new Move();
	Move testMove3 = new Move();
	Move testMove4 = new Move();
	
	@Test
	public void test() {
		
		testStack.setGameId(gameid);
		
		/*
		 * create & test stack with 6 actioncards
		 */
		testActionCardClimb.setCardNum(1);
		testActionCardClimb.setCharacter(ECharacter.BELLE);
		testActionCardClimb.seteAction(EAction.CLIMB);
		testActionCardClimb.setGameId(gameid);
		testActionCardClimb.setUserId(userid);
		testActionCardClimb.setVisible(true);
		
		testActionCardMarshal.setCardNum(2);
		testActionCardMarshal.setCharacter(ECharacter.CHEYENNE);
		testActionCardMarshal.seteAction(EAction.MARSHAL);
		testActionCardMarshal.setGameId(gameid);
		testActionCardMarshal.setUserId(userid);
		testActionCardMarshal.setVisible(true);
		
		testActionCardMove.setCardNum(3);
		testActionCardMove.setCharacter(ECharacter.DJANGO);
		testActionCardMove.seteAction(EAction.MOVE);
		testActionCardMove.setGameId(gameid);
		testActionCardMove.setUserId(userid);
		testActionCardMove.setVisible(true);
		
		testActionCardPunch.setCardNum(4);
		testActionCardPunch.setCharacter(ECharacter.DOC);
		testActionCardPunch.seteAction(EAction.PUNCH);
		testActionCardPunch.setGameId(gameid);
		testActionCardPunch.setUserId(userid);
		testActionCardPunch.setVisible(true);
		
		testActionCardRob.setCardNum(5);
		testActionCardRob.setCharacter(ECharacter.GHOST);
		testActionCardRob.seteAction(EAction.ROB);
		testActionCardRob.setGameId(gameid);
		testActionCardRob.setUserId(userid);
		testActionCardRob.setVisible(true);
		
		testActionCardShoot.setCardNum(6);
		testActionCardShoot.setCharacter(ECharacter.TUCO);
		testActionCardShoot.seteAction(EAction.SHOOT);
		testActionCardShoot.setGameId(gameid);
		testActionCardShoot.setUserId(userid);
		testActionCardShoot.setVisible(true);
		
		testStack.addActionCardToStack(testActionCardClimb);
		testStack.addActionCardToStack(testActionCardMarshal);
		testStack.addActionCardToStack(testActionCardMove);
		testStack.addActionCardToStack(testActionCardPunch);
		testStack.addActionCardToStack(testActionCardRob);
		testStack.addActionCardToStack(testActionCardShoot);
		
		Assert.assertEquals(testStack.getGameId(), gameid);
		
		Assert.assertEquals(testStack.getAcStack().size(), 6);
		
		for (int i = 0; i< testStack.getAcStack().size(); i++) {
			
			ActionCard cardAfter = new ActionCard();
			cardAfter = testStack.getAcStack().get(i);
			
			Assert.assertEquals(cardAfter.getUserId(), userid);
			Assert.assertTrue(cardAfter.isVisible());				
			
		}
		
		Assert.assertEquals(EAction.CLIMB, testStack.getAcStack().get(0).geteAction());
		Assert.assertEquals(EAction.MARSHAL, testStack.getAcStack().get(1).geteAction());
		Assert.assertEquals(EAction.MOVE, testStack.getAcStack().get(2).geteAction());
		Assert.assertEquals(EAction.PUNCH, testStack.getAcStack().get(3).geteAction());
		Assert.assertEquals(EAction.ROB, testStack.getAcStack().get(4).geteAction());
		Assert.assertEquals(EAction.SHOOT, testStack.getAcStack().get(5).geteAction());

		Assert.assertEquals(ECharacter.BELLE, testStack.getAcStack().get(0).getCharacter());
		Assert.assertEquals(ECharacter.CHEYENNE, testStack.getAcStack().get(1).getCharacter());
		Assert.assertEquals(ECharacter.DJANGO, testStack.getAcStack().get(2).getCharacter());
		Assert.assertEquals(ECharacter.DOC, testStack.getAcStack().get(3).getCharacter());
		Assert.assertEquals(ECharacter.GHOST, testStack.getAcStack().get(4).getCharacter());
		Assert.assertEquals(ECharacter.TUCO, testStack.getAcStack().get(5).getCharacter());
	
		
		notInStack.setCardNum(2);
		notInStack.setCharacter(ECharacter.CHEYENNE);
		notInStack.seteAction(EAction.SHOOT);
		
		
		Assert.assertFalse(testStack.checkACIfInStack(notInStack));
		
		Assert.assertTrue(testStack.checkACIfInStack(testActionCardClimb));
		Assert.assertTrue(testStack.checkACIfInStack(testActionCardMarshal));
		Assert.assertTrue(testStack.checkACIfInStack(testActionCardMove));
		Assert.assertTrue(testStack.checkACIfInStack(testActionCardPunch));
		Assert.assertTrue(testStack.checkACIfInStack(testActionCardRob));
		Assert.assertTrue(testStack.checkACIfInStack(testActionCardShoot));
		
		testStack.removePlayedACCard();
		
		Assert.assertFalse(testStack.checkACIfInStack(testActionCardClimb));
		
		Assert.assertEquals(testStack.getFirstActionCard(), testActionCardMarshal);
				
		Assert.assertEquals(ECharacter.TUCO, testStack.getLastActionCardFromStack().getCharacter());

		
		
		/*
		 * create and test stack with 5 roundcards
		 */
		
		testRoundCardAM.setCardNum(1);
		testRoundCardAM.setCurrentTurn(1);
		testRoundCardAM.setGameId(gameid);
		testRoundCardAM.setMaxTurns(4);
		
		testSpecialEvent.setSpecialEvent(ESpecialEvent.ANGRY_MARSHAL);

		testRoundCardAM.setSpecialEvent(testSpecialEvent);
		
		testRoundCardBrake.setCardNum(2);
		testRoundCardBrake.setCurrentTurn(1);
		testRoundCardBrake.setGameId(gameid);
		testRoundCardBrake.setMaxTurns(4);
		
		testSpecialEvent.setSpecialEvent(ESpecialEvent.BRAKE);
	
		testRoundCardBrake.setSpecialEvent(testSpecialEvent);
		
		testRoundCardCrane.setCardNum(3);
		testRoundCardCrane.setCurrentTurn(1);
		testRoundCardCrane.setGameId(gameid);
		testRoundCardCrane.setMaxTurns(4);
		
		testSpecialEvent.setSpecialEvent(ESpecialEvent.CRANE);
		
		testRoundCardCrane.setSpecialEvent(testSpecialEvent);		
		
		
		testRoundCardHostage.setCardNum(4);
		testRoundCardHostage.setCurrentTurn(1);
		testRoundCardHostage.setGameId(gameid);
		testRoundCardHostage.setMaxTurns(4);
		
		testSpecialEvent.setSpecialEvent(ESpecialEvent.HOSTAGE);
		
		testRoundCardHostage.setSpecialEvent(testSpecialEvent);
		

		testRoundCardNothing.setCardNum(5);
		testRoundCardNothing.setCurrentTurn(1);
		testRoundCardNothing.setGameId(gameid);
		testRoundCardNothing.setMaxTurns(4);
		
		testSpecialEvent.setSpecialEvent(ESpecialEvent.NOTHING);
		
		testRoundCardNothing.setSpecialEvent(testSpecialEvent);
		
		
		testStack.addRoundCardToStack(testRoundCardAM);
		testStack.addRoundCardToStack(testRoundCardBrake);
		testStack.addRoundCardToStack(testRoundCardCrane);
		testStack.addRoundCardToStack(testRoundCardHostage);
		testStack.addRoundCardToStack(testRoundCardNothing);
		
		
		Assert.assertEquals(testStack.getRcStack().size(), 5);
		
		Assert.assertEquals(testStack.getRoundCardOfRound(1), testRoundCardAM);
		

		Round testRound = new Round();
		testRound.setCurrentRound(ERound.ONE);
		
		Assert.assertEquals(testRoundCardNothing.getCardNum(), testStack.getCurrentRoundCard(testRound).getCardNum());

		testRound.setCurrentRound(ERound.TWO);
		
		Assert.assertEquals(testRoundCardHostage.getCardNum(), testStack.getCurrentRoundCard(testRound).getCardNum());

		testRound.setCurrentRound(ERound.THREE);
		
		Assert.assertEquals(testRoundCardCrane.getCardNum(), testStack.getCurrentRoundCard(testRound).getCardNum());
		
		testRound.setCurrentRound(ERound.FOUR);
		
		Assert.assertEquals(testRoundCardBrake.getCardNum(), testStack.getCurrentRoundCard(testRound).getCardNum());

		testRound.setCurrentRound(ERound.FIVE);
		
		Assert.assertEquals(testRoundCardAM.getCardNum(), testStack.getCurrentRoundCard(testRound).getCardNum());

		testRound.setCurrentRound(ERound.END);
		
		Assert.assertEquals(null, testStack.getCurrentRoundCard(testRound));
		
		/*
		 * create & test stack with lockbox
		 */
				
		Lockbox testLockBox = new Lockbox();
		testLockBox.setGameId(gameid);
		testLockBox.setUserId(userid);
		
		testStack.setLockbox(testLockBox);
		
		Assert.assertEquals(testLockBox, testStack.getLockbox());
			
		/*
		 *  create and test stack with 6 ammocards
		 */
		
		testStack.addAmmoCard(testAmmoCard1);
		testStack.addAmmoCard(testAmmoCard2);
		testStack.addAmmoCard(testAmmoCard3);
		testStack.addAmmoCard(testAmmoCard4);
		testStack.addAmmoCard(testAmmoCard5);
		testStack.addAmmoCard(testAmmoCard6);
		
		Assert.assertEquals(6, testStack.getAmcStack().size());
		
		Assert.assertEquals(testAmmoCard6, testStack.getAmmoCard());
		
		/*
		 * create and test stack with 4 moves
		 */
		testMove1.setGameId(gameid);
		testMove1.setUserId(userid);
		testMove2.setGameId(gameid);
		testMove2.setUserId(userid);		
		testMove3.setGameId(gameid);
		testMove3.setUserId(userid);	
		testMove4.setGameId(gameid);
		testMove4.setUserId(userid);	
		
		testStack.addMove(testMove1);
		testStack.addMove(testMove2);		
		testStack.addMove(testMove3);
		testStack.addMove(testMove4);
		
		Assert.assertEquals(4, testStack.getMoveStack().size());
		Assert.assertEquals(testMove4, testStack.getLastMove());
		Assert.assertEquals(4, testStack.getMoveStack().size());
		
		testStack.clearPlayedMove();
		Assert.assertEquals(testMove3, testStack.getLastMove());
		Assert.assertEquals(3, testStack.getMoveStack().size());
		
		testStack.removeMove(testMove3);
		Assert.assertEquals(testMove2, testStack.getLastMove());
		Assert.assertEquals(2, testStack.getMoveStack().size());


		Assert.assertEquals(null, testStack.getRoundCardOfRound(0));
		
		/*
		 * let clearActionCardStack delete all the actioncards
		 */
		testStack.clearActionCardStack();
		/*
		 * check that there are no actioncards and that no specific actioncard is returend
		 */
		Assert.assertEquals(0,testStack.getAcStack().size());
		Assert.assertEquals(null, testStack.getLastActionCardFromStack());
		Assert.assertEquals(null, testStack.getFirstActionCard());
		Assert.assertEquals(null, testStack.getRoundCardOfRound(1));
		
		/*
		 * 
		 */
		
		/*
		 * remove all ammocards
		 */
		testStack.removeLastAmmoCard();
		testStack.removeLastAmmoCard();
		testStack.removeLastAmmoCard();
		testStack.removeLastAmmoCard();
		testStack.removeLastAmmoCard();
		testStack.removeLastAmmoCard();
		/*
		 * check that there are no ammocards left
		 */
		Assert.assertEquals(null, testStack.getAmmoCard());
		/*
		 * let clearMoveStack remove all moves
		 */
		testStack.clearMoveStack();
		/*
		 * check that there are no moves in the stack and that no move is returned
		 */
		Assert.assertEquals(0, testStack.getMoveStack().size());
		Assert.assertEquals(null, testStack.getLastMove());
		
		testStack.addRansom(1);
		testStack.addRansom(2);
		List<Long> testRansom = new ArrayList<Long>();
		testRansom.add(new Long(1));
		testRansom.add(new Long(2));
		assertEquals(testStack.getRansom(), testRansom);
	}

}
