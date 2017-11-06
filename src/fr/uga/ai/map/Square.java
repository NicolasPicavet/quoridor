package fr.uga.ai.map;

import fr.uga.ai.map.elements.Element;

public class Square {

    Element element;

    @Override
    public String toString() {
        return element != null ? element.toString() : " ";
    }

    public Element getElement() {
        return element;
    }

    public void removeElement() {
        this.element = null;
    }

    public boolean setElement(Element element) {
        if (this.element != null)
            return false;
        this.element = element;
        return true;
    }
}
