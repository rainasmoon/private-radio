package com.blogspot.rainasmoon.familytree.service.map;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.blogspot.rainasmoon.familytree.ui.FamilyUI;
import com.blogspot.rainasmoon.familytree.ui.NodeUI;
import com.blogspot.rainasmoon.familytree.vo.Graph;
import com.blogspot.rainasmoon.familytree.vo.Node;

@Component
@Transactional
public interface MapManager {

    public Node getNode();

    public void openNode();

    public void closeNode();

    public Graph getGraph();

    public boolean isPersonModel();

    public boolean isMarriageMode();

    public boolean isFamilyModel();

    public Node searchFamily(long peopleId, int level, String status);
    
    public NodeUI createFamilyUI(String fartherName);

	public NodeUI createHisParentUI(String name);
    
}
