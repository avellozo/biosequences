%%
%% graphicx and color include
%%
\makeatletter
\@ifundefined{pdfoutput}% Definitely not using pdftex.
{% Standard TeX
\usepackage[dvips]{graphicx,color}
}
{%  Running pdftex.
  \ifnum\pdfoutput=0\relax% Are we outputting pdf?
    % No it is dvi output
    \usepackage[dvips]{graphicx,color}
  \fi
  \ifnum\pdfoutput=1\relax% Are we outputting pdf?
    % Yes it is pdf output
    \usepackage[pdftex]{graphicx,color}
  \fi
}
\makeatother
