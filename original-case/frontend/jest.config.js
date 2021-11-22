module.exports = {
  preset: 'jest-preset-angular',
  globals: {
    'ts-jest': {
      allowSyntheticDefaultImports: true,
      tsconfig: 'tsconfig.spec.json',
      isolatedModules: true,
    },
  },
  transform: {
    '^.+\\.js$': 'babel-jest',
  },
  moduleNameMapper: {
    "^~(.*)$": "<rootDir>/src/$1"
  },
  modulePaths:["<rootDir>/src/app/service"]
};


