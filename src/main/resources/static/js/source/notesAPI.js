export default class notesAPI {
    static async getAllNotes(selectedTags) {
        try {
            let notes = [];
            if (selectedTags.length == 0){
                notes = await axios.get('/rest/notes/get-all-notes');
            }
            else {
                notes = await axios.get('/rest/notes/get-all-notes', {
                    params: { "selectedTags": selectedTags },
                    paramsSerializer: {
                        indexes: null
                    }
                })
            }
            return notes.data.sort((a, b) => {
                return new Date(a.updatedTime) > new Date(b.updatedTime) ? -1 : 1;
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

    static async deleteNote(noteIdToDelete){
        await axios({
            method: 'POST',
            url: '/rest/notes/delete-note',
            data: {
                noteId : noteIdToDelete
            }
        })
        .then(function (response) {
            console.log(response);
          })
          .catch(function (error) {
            console.log(error);
          });
    }

    static async collectAvailableTags(){
        try {
            const tags = await axios.get('/rest/notes/get-all-available-tags');
            return tags.data;
        } catch (error) {
            console.error(error);
        }
    }
}