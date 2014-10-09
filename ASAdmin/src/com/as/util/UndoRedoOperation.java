package com.as.util;

/**
 *
 * @author Nick Mukhin
 */
public interface UndoRedoOperation {
    boolean isUndone();
    boolean undo() throws Exception;
    boolean redo() throws Exception;
}
