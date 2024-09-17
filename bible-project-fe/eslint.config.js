module.exports = {
  extends: [
    'eslint:recommended',
    'plugin:import/errors',
    'plugin:import/warnings',
    'plugin:import/typescript',
    'prettier',
  ],
  plugins: ['import'],
  rules: {
    'import/order': [
      'error',
      {
        groups: [
          ['builtin', 'external'],
          ['index', 'sibling', 'parent', 'internal'],
        ],
        pathGroups: [
          {
            pattern: 'angular/**',
            group: 'external',
            position: 'before',
          },
          {
            pattern: 'jetty/**',
            group: 'external',
            position: 'after',
          },
          {
            pattern: './**',
            group: 'internal',
            position: 'after',
          },
        ],
        pathGroupsExcludedImportTypes: ['angular', 'jetty'],
        'newlines-between': 'always',
        alphabetize: { order: 'asc', caseInsensitive: true },
      },
    ],
  },
};
