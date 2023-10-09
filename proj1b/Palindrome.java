public class Palindrome {
    public Deque<Character> wordToDeque(String word){

        Deque<Character> result = new ArrayDeque<>();

        if(word == null)
            return null;
        
        for(int i = 0; i < word.length(); i++){
            char current = word.charAt(i);
            result.addLast(current);
        }
    
        return result;
    }

    private boolean isHelp(Deque dl){
        if (dl.size() == 0 || dl.size() == 1){
            return true;
        }

        Object last = dl.removeLast();
        Object first = dl.removeFirst();
        if(last != first)
            return false;

        return isHelp(dl);
    }
    public boolean isPalindrome(String word){
        if(word == null)
            return true;
        Deque lst = wordToDeque(word);
        return isHelp(lst);
    }

    public boolean isPalindrome(String word, CharacterComparator cc){
        int length = word.length();
        int i = 0; int j = length - 1;
        while(i < j){
            if(!cc.equalChars(word.charAt(i), word.charAt(j)))
                return false;
            i++;
            j--;
        }
        return true;
    }
}
