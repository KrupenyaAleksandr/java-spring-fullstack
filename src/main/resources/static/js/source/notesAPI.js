export default class notesAPI {
    static async getAllNotes() {
        try {
            const notes = await axios.get('/rest/notes/get-all-notes');
            console.log(notes);
            return notes.sort((a, b) => {
                return new Date(a.updated) > new Date(b.updated) ? -1 : 1;
            });
        } catch (error) {
            console.error(error);
        }
    }

    static async saveNote(noteToSave) {
        await axios.post('/rest/notes/save-note', noteToSave)
        .then(function (response) {
            console.log(response);
          })
          .catch(function (error) {
            console.log(error);
          });
    }

    static deleteNote(noteId){

    }
}