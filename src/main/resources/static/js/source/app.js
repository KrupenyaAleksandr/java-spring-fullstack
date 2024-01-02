import notesAPI from "./notesAPI.js";

notesAPI.getAllNotes()
notesAPI.saveNote({
    'title': 'New note',
    'text': 'New text'
});
notesAPI.saveNote({
    'noteId': 10,
    'title': 'Updated note',
    'text': 'Updated text'
});