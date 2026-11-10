package com.leo.banzai;

import android.content.Context;
import android.util.AttributeSet;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.getcapacitor.CapacitorWebView;

/**
 * WebView que não esconde o teclado quando o próprio teclado abre uma janela.
 *
 * Ao segurar o espaço, o Gboard abre a janelinha de idiomas, que pega o foco da janela do app.
 * O WebView (Chromium) reage à perda de foco escondendo o teclado e, sem teclado, a janelinha
 * fecha antes de dar tempo de escolher o idioma. Aqui, enquanto o teclado está aberto, a perda
 * de foco da janela não é repassada ao WebView; o Android esconde o teclado sozinho se outro app
 * de fato assumir a tela.
 */
public class WebViewTeclado extends CapacitorWebView {

    private boolean perdaDeFocoIgnorada = false;

    public WebViewTeclado(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (!hasWindowFocus) {
            WindowInsetsCompat insets = ViewCompat.getRootWindowInsets(this);
            if (insets != null && insets.isVisible(WindowInsetsCompat.Type.ime())) {
                perdaDeFocoIgnorada = true;
                return;
            }
        } else if (perdaDeFocoIgnorada) {
            perdaDeFocoIgnorada = false;
            return; // o WebView nunca soube que perdeu o foco
        }
        super.onWindowFocusChanged(hasWindowFocus);
    }
}
