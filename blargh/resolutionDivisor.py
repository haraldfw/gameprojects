def main():
    yes_pool = ['yes', 'true', 'roger roger', 'roger']
    width = str(input('Pixel width: '))
    if width.find('x') != -1:
        res = width.split('x')
        width = float(res[0])
        height = float(res[1])
    else:
        width = float(width)
        height = float(input('Pixel height: '))
    print('Aspect ratio is: ' + str(width / height))
    mwl_input = input('Maintain whole numbers? ')
    if not mwl_input:
        maintain_whole_numbers = False
    else:
        maintain_whole_numbers = str(mwl_input).strip().lower() in yes_pool

    while True:
        user_input = input('Enter divisor ("q" to quit): ')
        if user_input == 'q':
            break
        divisor = eval(user_input)
        new_width = width / divisor
        new_height = height / divisor
        if maintain_whole_numbers and (new_width % 1 != 0 or new_height % 1 != 0):
            print('Created non-whole number. Input ignored')
        else:
            width = new_width
            height = new_height
            print("New width: " + str(width) + "\nNew height: " + str(height))


if __name__ == '__main__':
    main()
