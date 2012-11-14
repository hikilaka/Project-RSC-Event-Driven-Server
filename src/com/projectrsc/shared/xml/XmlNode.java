package com.projectrsc.shared.xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A class which represents a single node in the DOM tree, maintaining
 * information about its children, attributes, value and name.
 */
public final class XmlNode implements Iterable<XmlNode> {

	/**
	 * The name of this node.
	 */
	private String name;

	/**
	 * The value of this node, or {@code null} if it has no value.
	 */
	private String value;

	/**
	 * The attribute map.
	 */
	private final Map<String, String> attributes = new HashMap<String, String>();

	/**
	 * The list of child nodes.
	 */
	private final List<XmlNode> children = new ArrayList<XmlNode>();

	/**
	 * Creates a new {@link XmlNode} with the specified name.
	 * 
	 * @param name
	 *            The name of this node.
	 */
	public XmlNode(String name) {
		this.name = name;
	}

	/**
	 * Gets the name of this node.
	 * 
	 * @return The name of this node.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of this node.
	 * 
	 * @param name
	 *            The name of this node.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the value of this node.
	 * 
	 * @return The value of this node, or {@code null} if it has no value.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value of this node.
	 * 
	 * @param value
	 *            The value of this node.
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Removes the value of this node.
	 */
	public void removeValue() {
		this.value = null;
	}

	/**
	 * Checks if this node has a value.
	 * 
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean hasValue() {
		return value != null;
	}

	@Override
	public Iterator<XmlNode> iterator() {
		return children.iterator();
	}

	/**
	 * Gets a {@link Collection} of child {@link XmlNode}s.
	 * 
	 * @return The collection.
	 */
	public Collection<XmlNode> getChildren() {
		return Collections.unmodifiableCollection(children);
	}

	/**
	 * Gets the first child with the specified name.
	 * 
	 * @param name
	 *            The name of the child.
	 * @return The {@link XmlNode} if a child was found with a matching name,
	 *         {@code null} otherwise.
	 */
	public XmlNode getChild(String name) {
		for (XmlNode child : children) {
			if (child.getName().equals(name)) {
				return child;
			}
		}
		return null;
	}

	/**
	 * Gets a {@link Set} of attribute names.
	 * 
	 * @return The set of names.
	 */
	public Set<String> getAttributeNames() {
		return attributes.keySet();
	}

	/**
	 * Gets a {@link Set} of attribute map entries.
	 * 
	 * @return The set of entries.
	 */
	public Set<Map.Entry<String, String>> getAttributes() {
		return attributes.entrySet();
	}

	/**
	 * Gets an attribute by it's name.
	 * 
	 * @param name
	 *            The name of the attribute.
	 * @return The attribute's value, or {@code null} if it doesn't exist.
	 */
	public String getAttribute(String name) {
		return attributes.get(name);
	}

	/**
	 * Checks if an attribute with the specified name exists.
	 * 
	 * @param name
	 *            The attribute's name.
	 * @return {@code true} if an attribute with that name exists, {@code false}
	 *         otherwise.
	 */
	public boolean containsAttribute(String name) {
		return attributes.containsKey(name);
	}

	/**
	 * Adds an attribute. It will overwrite an existing attribute if it exists.
	 * 
	 * @param name
	 *            The name of the attribute.
	 * @param value
	 *            The value of the attribute.
	 */
	public void setAttribute(String name, String value) {
		attributes.put(name, value);
	}

	/**
	 * Removes an attribute.
	 * 
	 * @param name
	 *            The name of the attribute.
	 */
	public void removeAttribute(String name) {
		attributes.remove(name);
	}

	/**
	 * Removes all attributes.
	 */
	public void removeAllAttributes() {
		attributes.clear();
	}

	/**
	 * Adds a child {@link XmlNode}.
	 * 
	 * @param child
	 *            The child to add.
	 */
	public void addChild(XmlNode child) {
		children.add(child);
	}

	/**
	 * Removes a child {@link XmlNode}.
	 * 
	 * @param child
	 *            The child to remove.
	 */
	public void removeChild(XmlNode child) {
		children.remove(child);
	}

	/**
	 * Removes all children.
	 */
	public void removeAllChildren() {
		children.clear();
	}

	/**
	 * Gets the child count.
	 * 
	 * @return The number of child {@link XmlNode}s.
	 */
	public int getChildCount() {
		return children.size();
	}

	/**
	 * Gets the attribute count.
	 * 
	 * @return The number of attributes.
	 */
	public int getAttributeCount() {
		return attributes.size();
	}

}