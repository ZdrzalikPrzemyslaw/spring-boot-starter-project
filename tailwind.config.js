/** @type {import('tailwindcss').Config} */
module.exports = {
<<<<<<< HEAD
  content: ["./src/main/resources/templates/**/*.{html,js}"],
  theme: {
    extend: {},
  },
  plugins: [],
}
=======
  mode: process.env.NODE_ENV ? 'jit' : undefined,
  purge: ["./src/**/*.html", "./src/**/*.js"],
  darkMode: false,
  theme: {
    extend: {},
  },
  variants: {
    extend: {},
  },
  plugins: [],
}
>>>>>>> 7b239077edc9465679560b084f2e35e0fe8d156b
