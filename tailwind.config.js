/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/main/resources/templates/**/*.{html,js}"],
  theme: {
    extend: {
      colors: {
        accent: '#F4B840FF',
        lighttt: '#F8F8F8',
        default: {
          light: '#1A1A1A',
          DEFAULT: '#1A1A1A',
          dark: '#F3F4F6',
        },
        secondary: {
          light: '#475569',
          DEFAULT: '#475569',
          dark: '#94a3b8',
        },
      },
    },
  },
  variants: {
    opacity: ({ after }) => after(['disabled'])
  },
  plugins: [],
}

