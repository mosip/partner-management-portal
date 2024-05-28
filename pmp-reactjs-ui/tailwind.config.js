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
        'moderate-red': '#B43F3F',
        'snow-white': '#FCFCFC',
        'medium-gray': '#707070',
      }),
      textColor: theme => ({
        ...theme('colors'),
        'tory-blue': '#1447b2',
        'charcoal-gray': '#454545',
        'dim-gray': '#656565',
        'light-gray': '#818181',
        'snow-white': '#FCFCFC',
        'dark-blue': '#031640',
      }),
      fontFamily: {
        inter: ['Inter var', ...defaultTheme.fontFamily.sans],
      },
    },
  },
  plugins: [],
};
