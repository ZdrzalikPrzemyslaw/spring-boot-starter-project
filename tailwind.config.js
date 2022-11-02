/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/main/resources/templates/**/*.{html,js}"],
  theme: {
    extend: {},
  },
  variants: {
    opacity: ({ after }) => after(['disabled'])
  },
  plugins: [],
}

