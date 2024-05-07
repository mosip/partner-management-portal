/** @type {import('tailwindcss').Config} */
const defaultTheme = require('tailwindcss/defaultTheme');

module.exports = {
  content: [
    "./src/**/*",
  ],
  theme: {
    extend: {
      backgroundColor: theme => ({
        ...theme('colors'),
        'anti-flash-white': '#F1F3F6',
        'tory-blue': '#1447b2',
        'fruit-salad': '#589D4C',
        'moderate-red': '#B43F3F'
      }),
      fontFamily: {
        inter: ['Inter var', ...defaultTheme.fontFamily.sans],
      },
    },
  },
  plugins: [],
};
