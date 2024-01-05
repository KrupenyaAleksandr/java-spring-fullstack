export default class notesView {
    constructor(root, { onNoteSelect, onNoteAdd, onNoteEdit, onNoteDelete, onTagsChanged } = {}) {
        this.root = root;
        this.onNoteSelect = onNoteSelect;
        this.onNoteAdd = onNoteAdd;
        this.onNoteEdit = onNoteEdit;
        this.onNoteDelete = onNoteDelete;
        this.onTagsChanged = onTagsChanged;
        this.root.innerHTML = `
            <div class="notes__sidebar">
                <button class="notes__add" type="button">Новая заметка</button>
                <div class="notes__list"></div>
            </div>
            <div class="notes__preview">
                <input class="notes__tag" type="text" placeholder="Метка">
                <input class="notes__title" type="text" placeholder="Новый заголовок...">
                <textarea class="notes__body">Новая записка...</textarea>
            </div>
        `;

        const btnAddNote = this.root.querySelector(".notes__add");
        const inpTitle = this.root.querySelector(".notes__title");
        const inpBody = this.root.querySelector(".notes__body");
        const inpTag = this.root.querySelector(".notes__tag");

        btnAddNote.addEventListener("click", () => {
            this.onNoteAdd();
        });

        [inpTitle, inpBody, inpTag].forEach(inputField => {
            inputField.addEventListener("blur", () => {
                const updatedTitle = inpTitle.value.trim();
                const updatedBody = inpBody.value.trim();
                const updatedTag = inpTag.value.trim();

                this.onNoteEdit(updatedTitle, updatedBody, updatedTag);
            });
        });

        this.updateNotePreviewVisibility(false);
    }

    _createListItemHTML(noteId, title, body, tag, updatedTime) {
        const MAX_BODY_LENGTH = 60;
        if (tag == null)
            tag = '';

        return `
            <div class="notes__list-item" data-note-id="${noteId}">
                <div class="notes__small-title">${title}</div>
                <div class="notes__small-body">
                    ${body.substring(0, MAX_BODY_LENGTH)}
                    ${body.length > MAX_BODY_LENGTH ? "..." : ""}
                </div>
                <div class="notes__small-tag">
                    ${tag}
                </div>
                <div class="notes__small-updated">
                    ${updatedTime.toLocaleString(undefined, { dateStyle: "full", timeStyle: "short" })}
                </div>
            </div>
        `;
    }

    updateNoteList(notes) {
        const notesListContainer = this.root.querySelector(".notes__list");

        // Empty list
        notesListContainer.innerHTML = "";

        for (const note of notes) {
            const html = this._createListItemHTML(note.noteId, note.title, note.body, note.tag, new Date(note.updatedTime));

            notesListContainer.insertAdjacentHTML("beforeend", html);
        }

        // Add select/delete events for each list item
        notesListContainer.querySelectorAll(".notes__list-item").forEach(noteListItem => {
            noteListItem.addEventListener("click", () => {
                this.onNoteSelect(noteListItem.dataset.noteId);
            });

            noteListItem.addEventListener("dblclick", () => {
                const doDelete = confirm("Вы точно хотите удалить эту записку?");

                if (doDelete) {
                    this.onNoteDelete(noteListItem.dataset.noteId);
                }
            });
        });
    }

    updateActiveNote(note) {
        this.root.querySelector(".notes__title").value = note.title;
        this.root.querySelector(".notes__body").value = note.body;
        this.root.querySelector(".notes__tag").value = note.tag;

        this.root.querySelectorAll(".notes__list-item").forEach(noteListItem => {
            noteListItem.classList.remove("notes__list-item--selected");
        });

        this.root.querySelector(`.notes__list-item[data-note-id="${note.noteId}"]`).classList.add("notes__list-item--selected");
    }

    updateNotePreviewVisibility(visible) {
        this.root.querySelector(".notes__preview").style.visibility = visible ? "visible" : "hidden";
    }

    addTagsToList(tags) {
        const lileTemplate = 
        `<li>
            <a class="dropdown-item" href="#">
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="{{tag}}" id="{{tag}}" />
                    <label class="form-check-label" for="{{tag}}">{{tag}}</label>
                </div>
            </a>
        </li>`;
        let output = '';
        for (let tag of tags) {
            output += lileTemplate.replaceAll('{{tag}}', tag);
        };
        document.getElementById("dropdown-menu").innerHTML = output;

        const _checkboxes = document.querySelectorAll("input[type=checkbox]");
        _checkboxes.forEach( checkbox => {
            checkbox.addEventListener("change", () => {
                const updatedTags = Array.from(_checkboxes)
                .filter(i => i.checked)
                .map(i => i.value);
                this.onTagsChanged(updatedTags);
            });
        })
    };
}