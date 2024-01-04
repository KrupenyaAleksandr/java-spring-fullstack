import notesAPI from "./notesAPI.js";
import notesView from "./notesView.js";

export default class App {
    constructor(root) {
        this.notes = [];
        this.selectedTags = [];
        this.activeNote = null;
        this.view = new notesView(root, this._handlers());
        this.flag = false;
        this._refreshNotes();
    }

    async _refreshNotes() {
        const notes = await notesAPI.getAllNotes(this.selectedTags);

        this._setNotes(notes);
        if (!this.flag){
            const tags = await notesAPI.collectAvailableTags();
            this.view.addTagsToList(tags);
            if (notes.length > 0) {
                this._setActiveNote(notes[0]);
            }
            this.flag = true;
        }
    }

    _setNotes(notes) {
        this.notes = notes;
        this.view.updateNoteList(notes);
        this.view.updateNotePreviewVisibility(notes.length > 0);
    }

    _addAvailableTags(tags){
        this.view.addTagsToList(tags);
    }

    _setActiveNote(note) {
        this.activeNote = note;
        this.view.updateActiveNote(note);
    }

    _handlers() {
        return {
            onNoteSelect: noteId => {
                const selectedNote = this.notes.find(note => note.noteId == noteId);
                this._setActiveNote(selectedNote);
            },
            onNoteAdd: async () => {
                const newNote = {
                    title: "New Note",
                    body: "Take note..."
                };

                await notesAPI.saveNote(newNote);
                await this._refreshNotes();
            },
            onNoteEdit: async (title, body, tag) => {
                await notesAPI.saveNote({
                    noteId: this.activeNote.noteId,
                    tag,
                    title,
                    body,
                    tag
                });

                await this._refreshNotes();
            },
            onNoteDelete: async noteId => {
                await notesAPI.deleteNote(noteId);
                await this._refreshNotes();
            },
            onTagsChanged: async updatedTags => {
                this.selectedTags = updatedTags;
                await this._refreshNotes();
            }
        };
    }
}