package com.jbrown.jnet.core;

import java.util.EnumMap;

import com.jbrown.jnet.commands.action.ClearAction;
import com.jbrown.jnet.commands.action.GetAction;
import com.jbrown.jnet.commands.action.HelpAction;
import com.jbrown.jnet.commands.action.MathAction;
import com.jbrown.jnet.commands.action.NoAction;
import com.jbrown.jnet.commands.action.PingAction;
import com.jbrown.jnet.commands.action.SetAction;
import com.jbrown.jnet.commands.action.WGetAction;

public class ActionPerformer {
  private EnumMap<Command, Class> _actionPerformerMap;

  public ActionPerformer() {
    _actionPerformerMap = new EnumMap<Command, Class>(Command.class);

    _actionPerformerMap.put(Command.CLEAR, ClearAction.class);
    _actionPerformerMap.put(Command.PING, PingAction.class);
    _actionPerformerMap.put(Command.HELP, HelpAction.class);
    _actionPerformerMap.put(Command.CALC, MathAction.class);
    _actionPerformerMap.put(Command.WGET, WGetAction.class);
    _actionPerformerMap.put(Command.GET,  GetAction.class);
    _actionPerformerMap.put(Command.SET,  SetAction.class);
  }

  public Class getActionClass(RequestI request) {
    Class klass = _actionPerformerMap.get(request.getCommand());

    if(klass == null){
      klass = NoAction.class;
    }

    return klass;
  }
}
