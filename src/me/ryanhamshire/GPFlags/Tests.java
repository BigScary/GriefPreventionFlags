package me.ryanhamshire.GPFlags;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.List;

import org.bukkit.configuration.InvalidConfigurationException;
import org.junit.Test;

public class Tests
{
   @Test
   public void TrivialTest()
   {
       assertTrue(true);
   }
   
   @Test
   public void Manager_RegisterDefinition()
   {
       FlagManager manager = new FlagManager();
       
       FlagDefinition result = manager.GetFlagDefinitionByName("notRegistered");
       assertTrue(result == null);
       
       manager.RegisterFlagDefinition(new TestFlagDefinition(manager, "TestDef"));
       
       result = manager.GetFlagDefinitionByName("TESTDEF");
       assertTrue(result.getName().equals("TestDef"));
   }
   
   @Test
   public void Manager_ListDefs()
   {
       FlagManager manager = new FlagManager();
       manager.RegisterFlagDefinition(new TestFlagDefinition(manager, "TestDef"));
       manager.RegisterFlagDefinition(new TestFlagDefinition(manager, "TestDef2"));
       manager.RegisterFlagDefinition(new TestFlagDefinition(manager, "TestDef2"));
       manager.RegisterFlagDefinition(new TestFlagDefinition(manager, "TestDef2"));
       manager.RegisterFlagDefinition(new TestFlagDefinition(manager, "TestDef"));
       
       Collection<FlagDefinition> defs = manager.GetFlagDefinitions();
       assertTrue(defs.size() == 2);
   }
   
   @Test
   public void Manager_SaveLoad() throws InvalidConfigurationException
   {
       //setup
       FlagManager manager = new FlagManager();
       TestFlagDefinition def = new TestFlagDefinition(manager, "TestDef");
       TestFlagDefinition def2 = new TestFlagDefinition(manager, "TestDef2");
       manager.RegisterFlagDefinition(def);
       manager.RegisterFlagDefinition(def2);
       
       //populate 
       manager.SetFlag("1", def, true, "asdf");
       
       manager.SetFlag("2", def, false, "asdf");
       manager.SetFlag("2", def2, true, "params");
       
       manager.SetFlag("3", def2, true, "removeme");
       manager.UnSetFlag("3", def2);
       
       //save
       String fileContent = manager.FlagsToString();
       
       FlagManager manager2 = new FlagManager();
       manager2.RegisterFlagDefinition(def);
       manager2.RegisterFlagDefinition(def2);
       
       //load
       List<MessageSpecifier> errors = manager2.Load(fileContent);
       assertTrue(errors.size() == 0);
       
       //verify
       assertTrue(manager2.GetFlags("1").size() == 1);
       assertTrue(manager2.GetFlags("2").size() == 2);
       assertTrue(manager2.GetFlags("3").size() == 0);
       Flag flag = manager2.GetFlag("2", def2);
       assertTrue(flag.parameters.equals("params"));
   }
   
   @Test
   public void FlagDef_SetupOnceTeardownOnce()
   {
       FlagManager manager = new FlagManager();
       TestFlagDefinition def = new TestFlagDefinition(manager, "TestDef");
       manager.RegisterFlagDefinition(def);
       assertTrue(def.setupInvocations == 0);
       manager.SetFlag("1", def, true, "");
       assertTrue(def.setupInvocations == 1);
       manager.SetFlag("2", def, true, "");
       assertTrue(def.setupInvocations == 1);
       manager.UnSetFlag("1", def);
       manager.UnSetFlag("2", def);
       manager.UnSetFlag("2", def);
   }
   
   @Test
   public void Flag_CreateReadDelete()
   {
       //setup
       FlagManager manager = new FlagManager();
       TestFlagDefinition def = new TestFlagDefinition(manager, "TestDef");
       manager.RegisterFlagDefinition(def);
       
       //starting state
       assertTrue(manager.GetFlags("1").size() == 0);
       assertTrue(manager.GetFlag("1",  def) == null);
       
       //create 
       manager.SetFlag("1", def, true, "asdf");
       
       //read
       Flag flag = manager.GetFlag("1", def);
       assertTrue(flag != null);
       assertTrue(flag.flagDefinition.getName().equals("TestDef"));
       assertTrue(flag.getSet());
       assertTrue(flag.parameters.equals("asdf"));
       assertTrue(manager.GetFlags("1").size() == 1);
       
       //delete
       manager.UnSetFlag("1", def);
       
       //ending state
       assertTrue(manager.GetFlags("1").size() == 0);
       assertTrue(manager.GetFlag("1",  def) == null);
   }
   
   @Test
   public void SettingsManager_SetGet()
   {
       WorldSettingsManager manager = new WorldSettingsManager();
       WorldSettings settings = manager.Get("notSetYet");
       assertTrue(settings != null);
       assertTrue(!settings.pvpRequiresClaimFlag);
       assertTrue(settings.pvpDeniedMessage == null);
       settings.pvpDeniedMessage = "asdf";
       settings = manager.Get("notSetYet");
       assertTrue(settings.pvpDeniedMessage.equals("asdf"));
       settings = new WorldSettings();
       settings.pvpRequiresClaimFlag = true;
       manager.Set("world", settings);
       settings = manager.Get("world");
       assertTrue(settings.pvpRequiresClaimFlag);
   }
   
   private class TestFlagDefinition extends FlagDefinition
   {
       public int setupInvocations;
       private String name;

       TestFlagDefinition(FlagManager manager, String name)
       {
           super(manager, null);
           this.name = name;
       }
        
       @Override
       String getName()
       {
           return this.name;
       }
        
       @Override
       MessageSpecifier GetSetMessage(String parameters)
       {
           return new MessageSpecifier(Messages.NoFlagsInThisClaim, "cat", "dog");
       }
        
       @Override
       MessageSpecifier GetUnSetMessage()
       {
           return new MessageSpecifier(Messages.ThatFlagNotSet, "cat", "dog");
       }
        
       @Override
       void FirstTimeSetup()
       {
           this.setupInvocations++;        
       }
   }
}
