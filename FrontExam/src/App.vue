<template>
  <b-container id="app" style="height: 2000px;">
    <!-- Carrusel -->
    <b-row class="my-5">
      <b-col cols="12">
        <b-carousel id="carousel-no-animation" style="text-shadow: 0px 0px 2px #000;" no-animation indicators
          img-width="800" img-height="400">
          <div v-for="book in booksCover" :key="book.id">
            <b-carousel-slide :caption="book.name" :img-src="book.cover"></b-carousel-slide>
          </div>
        </b-carousel>
      </b-col>
    </b-row>

    <b-row style="display: flex; align-items: center; justify-content: center;" class="my-2">
      <b-col cols="4">
        <b-button @click="filterBooks('author')">Ordenar por autor</b-button>
      </b-col>
      <b-col cols="4">
        <b-button @click="filterBooks('publishDate')">Ordenar por fecha</b-button>
      </b-col>
      <b-col cols="4">
        <b-button @click="filterBooks('cover')">Mostrar si tiene imagen</b-button>
      </b-col>
    </b-row>

    <b-row>
      <b-col cols="10">
        <b-row>
          <TransitionGroup name="zoom" tag="div" class="d-flex flex-row flex-wrap">
            <b-col v-for="book in books" :key="book.id">
              <b-card :title="book.name" :img-src="book.cover" img-alt="Image" img-top tag="article"
                style="max-width: 20rem; min-width: 20rem;" class="mb-2" draggable @dragstart="startDrag($event, book)">
                <b-card-text>Autor: {{ book.author }}</b-card-text>
                <b-card-text>Fecha de publicación: {{ book.publishDate }}</b-card-text>
              </b-card>
            </b-col>
          </TransitionGroup>
        </b-row>
      </b-col>
      <b-col cols="2" class="p-5">
        <b-row>
          <b-col cols="12" class="my-2">
            <b-button v-b-modal.form>+</b-button>
          </b-col>
          <b-col cols="12" class="mb-2">
            <div class="drop-zone" @drop="update($event)" @dragover.prevent @dragenter.prevent>
              <b-icon icon="pencil"></b-icon>
            </div>
          </b-col>
          <b-col cols="12">
            <div class="drop-zone" @drop="deleteBook($event)" @dragover.prevent @dragenter.prevent>
              <b-icon icon="trash"></b-icon>
            </div>
          </b-col>
        </b-row>
      </b-col>
    </b-row>

    <!-- Modal de registro -->
    <b-modal id="form" title="Formulario" hide-footer size="lg">
      <form @submit.prevent="handleSubmit">
        <b-row>
          <b-col cols="12" class="my-2">
            <b-form-group label="Nombre" label-for="name">
              <b-form-input id="name" v-model="book.name" required></b-form-input>
            </b-form-group>
          </b-col>
          <b-col cols="12" class="mb-2">
            <b-form-group label="Autor" label-for="author">
              <b-form-input id="author" v-model="book.author" required></b-form-input>
            </b-form-group>
          </b-col>
          <b-col cols="12" class="mb-2">
            <b-form-group label="Fecha de publicación" label-fro="publishDate">
              <b-form-datepicker id="publishDate" v-model="book.publishDate" required></b-form-datepicker>
            </b-form-group>
          </b-col>
          <b-col cols="12" class="mb-2">
            <b-form-file v-model="file" placeholder="Elige la portada..." drop-placeholder="Arrastra el la imagen aquí..."
              accept="image/jpeg, image/png"></b-form-file>
          </b-col>
          <b-col cols="12">
            <b-row class="d-flex flex-row-reverse">
              <b-button type="submit" variant="success" class="mx-3">Registrar</b-button>
              <b-button variant="danger" @click="resetModal">Cancelar</b-button>
            </b-row>
          </b-col>
        </b-row>
      </form>
    </b-modal>

    <!-- Modal de actualización -->
    <b-modal id="update-form" title="Formulario" hide-footer size="lg">
      <form @submit.prevent="handleUpdate">
        <b-row>
          <b-col cols="12" class="my-2">
            <b-form-group label="Nombre" label-for="name">
              <b-form-input id="name" v-model="updateBook.name" required></b-form-input>
            </b-form-group>
          </b-col>
          <b-col cols="12" class="mb-2">
            <b-form-group label="Autor" label-for="author">
              <b-form-input id="author" v-model="updateBook.author" required></b-form-input>
            </b-form-group>
          </b-col>
          <b-col cols="12" class="mb-2">
            <b-form-group label="Fecha de publicación" label-fro="publishDate">
              <b-form-datepicker id="publishDate" v-model="updateBook.publishDate" required></b-form-datepicker>
            </b-form-group>
          </b-col>
          <b-col cols="12" class="mb-2">
            <b-form-file v-model="fileUpdate" placeholder="Elige la portada..."
              drop-placeholder="Arrastra el la imagen aquí..." accept="image/jpeg, image/png"></b-form-file>
          </b-col>
          <b-col cols="12">
            <b-row class="d-flex flex-row-reverse">
              <b-button type="submit" variant="success" class="mx-3">Actualizar</b-button>
              <b-button variant="danger" @click="resetModal">Cancelar</b-button>
            </b-row>
          </b-col>
        </b-row>
      </form>
    </b-modal>
  </b-container>
</template>

<script>
import LibrosService from "./Services/LibrosService";
import Functions from './utils/functions'
export default {
  data() {
    return {
      showElement: true,
      books: [],
      booksCover: [],
      lastScrollPosition: 0,
      file: null,
      fileUpdate: null,
      book: {
        id: 0,
        name: "",
        author: "",
        publishDate: "",
        cover: null,
      },
      updateBook: {
        id: 0,
        name: "",
        author: "",
        publishDate: "",
        cover: null,
      },
      params: {
        size: null,
        page: null,
        sort: "id",
        direction: "asc",
      },
    };
  },
  methods: {
    resetModal() {
      for (const field in this.book) {
        this.book[field] = null
      }
      for (const field in this.updateBook) {
        this.updateBook[field] = null
      }
      this.$bvModal.hide("form");
      this.$bvModal.hide("update-form");
    },
    async handleSubmit() {
      try {
        if (this.file != null)
          this.book.cover = await Functions.blobToBase64(this.file, "image/jpeg");
        const res = await LibrosService.saveBook(this.book);
        if (!res.error) this.getBooks()
      } catch (error) {
        console.error(error);
      } finally {
        this.$bvModal.hide("form");
        this.resetModal()
      }
    },
    async handleUpdate() {
      try {
        const cover = await Functions.blobToBase64(this.fileUpdate, "image/jpeg");
        const res = await LibrosService.updateBook({ ...this.updateBook, cover: cover });
        if (!res.error) this.getBooks();
      } catch (error) {
        console.error(error);
      } finally {
        this.$bvModal.hide("update-form");
        this.resetModal()
      }
    },
    async getBooks() {
      try {
        const { data: res } = await LibrosService.getBooks(this.params);
        this.books = res.content;
      } catch (error) {
        console.error(error);
      }
    },
    async getBooksCover() {
      try {
        const { data: res } = await LibrosService.getBooksCover(this.params);
        this.booksCover = res.content;
      } catch (error) {
        console.error(error);
      }
    },
    async filterBooks(sort) {
      try {
        if (sort == "author") {
          this.params = { ...this.params, sort: "author" }
          const { data: res } = await LibrosService.getBooks(this.params);
          this.books = res.content;
        }
        if (sort == "publishDate") {
          this.params = { ...this.params, sort: "publishDate" }
          const { data: res } = await LibrosService.getBooks(this.params);
          this.books = res.content;
        }
        if (sort == "cover") {
          const { data: res } = await LibrosService.getBooksCover(this.params);
          this.books = res.content;
        }
      } catch (error) {
        console.error(error);
      }
    },
    onScroll() {
      // Obtiene la posición actual del scroll
      const currentScrollPosition = window.pageYOffset || document.documentElement.scrollTop;

      // Determina la dirección del desplazamiento
      const scrollingDown = currentScrollPosition > this.lastScrollPosition;

      // Si el scroll es hacia abajo y la posición actual no es 0, oculta el elemento
      if (scrollingDown && currentScrollPosition !== 0) {
        this.showElement = false;
      } else {
        // Si el scroll es hacia arriba o la posición actual es 0, muestra el elemento
        this.showElement = true;
      }

      // Actualiza la última posición de desplazamiento
      this.lastScrollPosition = currentScrollPosition;
    },
    startDrag(evt, item) {
      evt.dataTransfer.dropEffect = 'move'
      evt.dataTransfer.effectAllowed = 'move'
      evt.dataTransfer.setData('itemID', item.id)
    },
    async update(evt) {
      const itemID = evt.dataTransfer.getData('itemID')
      try {
        const res = await LibrosService.getBook(itemID)
        if (res.error) throw new Error()
        this.updateBook = res.data;
        this.fileUpdate = Functions.base64toFile(this.updateBook.cover, "cover.jpg");
        this.$bvModal.show("update-form");
      } catch (error) {
        this.$bvModal.hide("update-form");
        this.resetModal()
        console.error(error);
      }
    },
    async deleteBook(evt) {
      const itemID = evt.dataTransfer.getData('itemID')
      try {
        const { data: res } = await LibrosService.deleteBook(itemID);
        if (res) this.getBooks();
      } catch (error) {
        console.error(error);
      }
    },
  },
  mounted() {
    this.getBooks();
    this.getBooksCover();
    window.addEventListener("scroll", this.onScroll);
  },
  beforeDestroy() {
    window.removeEventListener("scroll", this.onScroll);
  },
};
</script>

<style>
.zoom-enter-active,
.zoom-leave-active {
  transition: transform 0.5s;
}

.zoom-enter,
.zoom-leave-to {
  transform: scale(0);
}

.drop-zone {
  background-color: #eee;
  margin-bottom: 10px;
  padding: 10px;
  height: 100px;
  width: 200px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 3rem;
}

.drag-el {
  background-color: #fff;
  margin-bottom: 10px;
  padding: 5px;
}
</style>