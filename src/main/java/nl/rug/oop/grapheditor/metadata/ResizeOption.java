package nl.rug.oop.grapheditor.metadata;

/**
 * This is a basic enumerator of the different resize options
 * It contains all possible ways to resize a node - from either side,
 * corner or not resizing them at all.
 */
public enum ResizeOption {
    NO_RESIZE,
    BOTTOM_RIGHT_CORNER,
    BOTTOM_LEFT_CORNER,
    TOP_RIGHT_CORNER,
    TOP_LEFT_CORNER,
    BOTTOM_SIDE,
    TOP_SIDE,
    LEFT_SIDE,
    RIGHT_SIDE
}