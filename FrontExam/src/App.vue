<template>
  <b-container id="apps">
    <b-row class="my-2">
      <b-col cols="12">
        <b-carousel
          id="carousel-no-animation"
          style="text-shadow: 0px 0px 2px #000"
          no-animation
          indicators
          img-width="1024"
          img-height="480"
        >
          <b-carousel-slide
            caption="First Slide"
            img-src="https://picsum.photos/1024/480/?image=10"
          ></b-carousel-slide>
          <b-carousel-slide
            caption="Second Slide"
            img-src="https://picsum.photos/1024/480/?image=12"
          ></b-carousel-slide>
          <b-carousel-slide
            caption="Third Slide"
            img-src="https://picsum.photos/1024/480/?image=22"
          ></b-carousel-slide>
          <b-carousel-slide
            caption="Fourth Slide"
            img-src="https://picsum.photos/1024/480/?image=23"
          ></b-carousel-slide>
        </b-carousel>
      </b-col>
    </b-row>
    <b-row
      style="display: flex; align-items: center, justify-content: center"
      class="my-2"
    >
      <b-col cols="4">
        <b-button>Ordenar por autor</b-button>
      </b-col>
      <b-col cols="4">
        <b-button>Ordenar por fecha</b-button>
      </b-col>
      <b-col cols="4">
        <b-button>Mostrar si tiene imagen</b-button>
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="8">
        <b-row>
          <b-col cols="4" v-for="book in books" :key="book.id">
            <b-card
              :title="book.name"
              :img-src="book.cover"
              img-alt="Image"
              img-top
              tag="article"
              style="max-width: 20rem"
              class="mb-2"
            >
              <b-card-text>Autor: {{ book.author }}</b-card-text>
              <b-card-text
                >Fecha de publicación: {{ book.publishDate }}</b-card-text
              >
              <b-button href="#" variant="primary">Go somewhere</b-button>
            </b-card>
          </b-col>
        </b-row>
      </b-col>
      <b-col cols="4" class="p-5">
        <b-row>
          <b-col cols="12" class="my-2">
            <b-button>+</b-button>
          </b-col>
          <b-col cols="12" class="mb-2">
            <b-button>Editar</b-button>
          </b-col>
          <b-col cols="12">
            <b-button>Eliminar</b-button>
          </b-col>
        </b-row>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import LibrosService from "./Services/LibrosService";
export default {
  data() {
    return {
      book: {
        name: "",
        author: "",
        publicationDate: "",
        cover: "",
      },
      books: [],
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
      this.book = {
        name: "",
        author: "",
        publicationDate: "",
        cover: "",
      };
    },
    handleSubmit() {
      if (this.$refs.form.checkValidity()) {
        this.$bvModal.hide("modal-1");
        // Aquí puedes hacer lo que necesites con los datos del libro
        console.log("Datos del libro:", this.book);
      }
    },
    async getBooks() {
      try {
        const { data: res } = await LibrosService.getBooks(this.params);
        console.log(res);
        this.books = res.content;
      } catch (error) {
        console.error(error);
      }
    },
  },
  mounted() {
    this.getBooks();
  },
};
</script>
