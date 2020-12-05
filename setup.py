import os

def prep_test_folder():
    os.system('cp -r src testing')
    os.system('javac testing/*')
    os.system('cp -r Dictionaries/ testing/')

def compile_to_out():
    os.system('javac src/*')
    os.system('mkdir out')
    os.system('mv src/*.class out/')

def open_github():
    os.system('open https://github.com/olivercalder/WordHelp')
    os.system('xdg-open https://github.com/olivercalder/WordHelp')

def main():
    os.system('git clone https://github.com/olivercalder/WordHelp')
    os.chdir('WordHelp')

    done1 = False
    while not done1:
        comp = input('\nWould you like to compile source files into out folder? [Y/n] ')
        if comp == '' or comp.lower()[0] == 'y':
            compile_to_out()
            done1 = True
        elif comp.lower()[0] == 'n':
            done1 = True
        else:
            print('\nInput "{}" not recognized. Please try again.'.format(comp))

    done2 = False
    while not done2:
        test = input('\nWould you like to move source and class files, along with dictionaries, to a folder for testing? [Y/n] ')
        if test == '' or test.lower()[0] == 'y':
            prep_test_folder()
            done2 = True
        elif test.lower()[0] == 'n':
            done2 = True
        else:
            print('\nInput "{}" not recognized. Please try again.'.format(test))
    
    done3 = False
    while not done3:
        github = input('\nWould you like to open the github repository for this project? [Y/n] ')
        if github == '' or github.lower()[0] == 'y':
            open_github()
            done3 = True
        elif github.lower()[0] == 'n':
            done3 = True
        else:
            print('\nInput "{}" not recognized. Please try again.'.format(github))

    print('\nProject directory can be found in WordHelp')

main()
